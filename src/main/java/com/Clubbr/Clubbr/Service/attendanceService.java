package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.dto.attendanceDto;
import com.Clubbr.Clubbr.Repository.eventRepo;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Repository.attendanceRepo;
import com.Clubbr.Clubbr.Repository.userRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class attendanceService {

    @Autowired
    private eventRepo eventRepo;

    @Autowired
    private attendanceRepo attendanceRepo;

    @Autowired
    private stablishmentRepo stabRepo;

    @Autowired
    private MqttClient mqttClient;

    @Autowired
    private workerService workerService;

    @Autowired
    private userRepo userRepo;

    @Transactional
    public void attendanceControlWorkers(Long stabID, String eventName, LocalDate eventDate) throws JsonProcessingException, MqttException {
        List<attendance> attendanceList = new ArrayList<>();
        attendance attendance = new attendance();
        List<worker> workers = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        stablishment stab = stabRepo.findById(stabID).orElse(null);
        event existingEvent = eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, eventName, eventDate);

        workers = workerService.getAllWorkers(stab);

        // Crear una lista para almacenar los JSON
        List<ObjectNode> jsonList = new ArrayList<>();

        for(worker worker : workers){
            if(worker.getEventName() == existingEvent || worker.getEventName() == null){
                ObjectNode json = objectMapper.createObjectNode();
                json.put("Date", existingEvent.getEventDate().toString());
                json.put("Time", stab.getOpenTime().toString());
                json.put("StabName", stab.getStabName());
                json.put("StabAddress", stab.getStabAddress());
                json.put("EventName", existingEvent.getEventName());
                json.put("StabId", stab.getStablishmentID());
                json.put("TelegramID", userRepo.findById(worker.getUserID().getUserID()).orElse(null).getTelegramID());

                attendance.setUserID(worker.getUserID());
                attendance.setEventName(existingEvent);
                attendance.setStablishmentID(stab);
                attendance.setAttendance(false);

                attendanceList.add(attendance);

                jsonList.add(json);
            }
        }

        attendanceRepo.saveAll(attendanceList);
        // Convertir la lista de JSON a una cadena de texto JSON
        String jsonString = objectMapper.writeValueAsString(jsonList);

        // Publicar la cadena de texto JSON como un mensaje MQTT
        byte[] payload = jsonString.getBytes();
        MqttMessage mqttMessage = new MqttMessage(payload);
        //mqttClient.publish("Clubbr/AttendanceControl", mqttMessage);
        if (mqttClient != null) {
            mqttClient.publish("Clubbr/AttendanceControl", mqttMessage);
        } else {
            // Manejar la situación en la que mqttClient es null
            System.err.println("No se puede publicar el mensaje porque el cliente MQTT no está disponible");
        }
    }

    @Transactional
    public void updateAttendance(String telegramID, boolean attendance, String eventName, LocalDate eventDate, Long stabID) {
        // Buscas el usuario con el telegramID que recibiste
        user targetUser = userRepo.findByTelegramID(Long.parseLong(telegramID));
        stablishment stab = stabRepo.findById(stabID).orElse(null);
        event existingEvent = eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, eventName, eventDate);

        attendance attendanceToUpdate = attendanceRepo.findByUserIDAndEventNameAndStablishmentID(targetUser, existingEvent, stab);
        attendanceToUpdate.setAttendance(attendance);
        attendanceRepo.save(attendanceToUpdate);

    }

    @Transactional(readOnly = true)
    public List<attendanceDto> getWorkersAttendanceByEvent(Long stabID, String eventName, LocalDate eventDate) {
        stablishment stab = stabRepo.findById(stabID).orElse(null);
        event existingEvent = eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, eventName, eventDate);
        List<attendance> attendances = attendanceRepo.findAllByStablishmentIDAndEventName(stab, existingEvent);
        List<attendanceDto> attendanceDtos = attendances.stream().map(this::mapToAttendanceDto).toList();
        return attendanceDtos;
    }

    @Transactional(readOnly = true)
    public attendanceDto getWorkerAttendanceByEvent(Long stabID, String eventName, LocalDate eventDate, String userID) {
        stablishment stab = stabRepo.findById(stabID).orElse(null);
        event existingEvent = eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, eventName, eventDate);
        user targetUser = userRepo.findById(userID).orElse(null);
        attendance attendance = attendanceRepo.findByUserIDAndEventNameAndStablishmentID(targetUser, existingEvent, stab);
        attendanceDto attendanceDto = mapToAttendanceDto(attendance);
        return attendanceDto;
    }

    private attendanceDto mapToAttendanceDto(attendance attendance) {
        attendanceDto attendanceDto = new attendanceDto();
        attendanceDto.setAttendance(attendance.isAttendance());
        attendanceDto.setUserID(attendance.getUserID().getUserID());
        attendanceDto.setName(attendance.getUserID().getName());
        attendanceDto.setSurname(attendance.getUserID().getSurname());
        attendanceDto.setStabName(attendance.getStablishmentID().getStabName());
        attendanceDto.setEventName(attendance.getEventName().getEventName());
        attendanceDto.setEventDate(attendance.getEventName().getEventDate());
        return attendanceDto;
    }

    @Transactional
    public void updateAttendanceOfWorker(Long stabID, String eventName, LocalDate eventDate, String userID, attendance targetAttendance) {
        stablishment stab = stabRepo.findById(stabID).orElse(null);
        event existingEvent = eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, eventName, eventDate);
        user targetUser = userRepo.findById(userID).orElse(null);
        attendance attendanceToUpdate = attendanceRepo.findByUserIDAndEventNameAndStablishmentID(targetUser, existingEvent, stab);
        attendanceToUpdate.setAttendance(targetAttendance.isAttendance());
        attendanceRepo.save(attendanceToUpdate);
    }

    @Transactional
    public void deleteAttendancesOfWorker(Long stabID, String eventName, LocalDate eventDate, String userID) {
        stablishment stab = stabRepo.findById(stabID).orElse(null);
        event existingEvent = eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, eventName, eventDate);
        user targetUser = userRepo.findById(userID).orElse(null);
        List<attendance> attendancesToDelete = attendanceRepo.findAllByUserIDAndEventNameAndStablishmentID(targetUser, existingEvent, stab);
        attendanceRepo.deleteAll(attendancesToDelete);
    }

    @Transactional
    public void deleteAttendancesOfEvent(Long stabID, String eventName, LocalDate eventDate) {
        stablishment stab = stabRepo.findById(stabID).orElse(null);
        event existingEvent = eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, eventName, eventDate);
        List<attendance> attendancesToDelete = attendanceRepo.findAllByStablishmentIDAndEventName(stab, existingEvent);
        attendanceRepo.deleteAll(attendancesToDelete);
    }
}
