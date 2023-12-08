package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.*;
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
                // Añadir el JSON a la lista
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
}
