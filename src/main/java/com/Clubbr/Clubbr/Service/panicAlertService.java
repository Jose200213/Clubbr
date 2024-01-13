package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Dto.panicAlertDto;
import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.Repository.panicAlertRepo;
import com.Clubbr.Clubbr.Repository.workerRepo;
import com.Clubbr.Clubbr.advice.ResourceNotFoundException;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class panicAlertService {

    @Autowired
    private panicAlertRepo panicAlertRepo;

    @Autowired
    private userService userService;

    @Autowired
    private managerService managerService;

    @Autowired
    private MqttClient mqttClient;

    @Autowired
    private stablishmentService stabService;

    @Autowired
    private workerRepo workerRepo;

    @Autowired
    private jwtService jwtService;

    @Autowired
    private eventService eventService;


    @Transactional
    public void createPanicAlert(event targetEvent, String token) throws JsonProcessingException, MqttException {
        panicAlert newPanicAlert = new panicAlert();
        String userId = jwtService.extractUserIDFromToken(token);
        user alertUser = userService.getUser(userId);

        newPanicAlert.setEventName(targetEvent);
        newPanicAlert.setStablishmentID(targetEvent.getStablishmentID());
        newPanicAlert.setUserID(alertUser);
        newPanicAlert.setPanicAlertDateTime(LocalDateTime.now());

        List<worker> workers = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        stablishment stab = stabService.getStab(targetEvent.getStablishmentID().getStablishmentID());
        event existingEvent = eventService.getEventByStabNameDate(stab.getStablishmentID(), targetEvent.getEventName(), targetEvent.getEventDate());

        workers = workerRepo.findAllByStablishmentID(stab);
        if (workers.isEmpty()) {
            throw new ResourceNotFoundException("Trabajadores");
        }

        // Crear una lista para almacenar los JSON
        List<ObjectNode> jsonList = new ArrayList<>();

        // Formatear la hora, minutos y segundos sin nanosegundos
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        for(worker worker : workers){
            if(worker.getEventID() == existingEvent || worker.getEventID() == null){
                ObjectNode json = objectMapper.createObjectNode();
                json.put("Date", LocalDate.now().toString());
                json.put("Time", LocalTime.now().format(formatter).toString());
                json.put("StabName", stab.getStabName());
                json.put("EventName", existingEvent.getEventName());
                json.put("UserName", alertUser.getName());
                json.put("UserSurname", alertUser.getSurname());
                json.put("TelegramID", userService.getUser(worker.getUserID().getUserID()).getTelegramID());

                jsonList.add(json);
            }
        }

        String jsonString = objectMapper.writeValueAsString(jsonList);

        byte[] payload = jsonString.getBytes();
        MqttMessage mqttMessage = new MqttMessage(payload);
        if (mqttClient != null) {
            mqttClient.publish("Clubbr/PanicAlert", mqttMessage);
        } else {
            System.err.println("No se puede publicar el mensaje porque el cliente MQTT no está disponible");
        }

        panicAlertRepo.save(newPanicAlert);

    }

    @Transactional
    public void deletePanicAlertByIdFromStablishment(Long stablishmentID, Long panicAlertId, String token) {
        managerService.checkManagerIsFromStab(stablishmentID, token);

        panicAlertRepo.deleteById(panicAlertId);
    }

    public List<panicAlertDto> getPanicsListDto(List<panicAlert> panicAlerts){
        return panicAlerts.stream().map(panicAlertDto::new).collect(Collectors.toList());
    }

    public panicAlertDto getPanicDto(panicAlert panicAlert){
        return new panicAlertDto(panicAlert);
    }

    @Transactional(readOnly = true)
    public List<panicAlert> getPanicAlertsByStab(Long stabId, String token) {
        managerService.checkManagerIsFromStab(stabId, token);

        return panicAlertRepo.findAllByStablishmentID(stabService.getStab(stabId));
    }

    @Transactional(readOnly = true)
    public List<panicAlert> getPanicAlertsByStabAndUser(Long stabId, String userId, String token) {
        managerService.checkManagerIsFromStab(stabId, token);

        return panicAlertRepo.findAllByStablishmentIDAndUserID(stabService.getStab(stabId), userService.getUser(userId));
    }
}