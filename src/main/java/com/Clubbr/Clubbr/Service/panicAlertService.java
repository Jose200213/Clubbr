package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.Repository.eventRepo;
import com.Clubbr.Clubbr.Repository.workerRepo;
import com.Clubbr.Clubbr.Repository.panicAlertRepo;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Repository.userRepo;
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
    private userRepo userRepo;

    @Autowired
    private stablishmentRepo stabRepo;

    @Autowired
    private stablishmentService stabService;

    @Autowired
    private eventRepo eventRepo;

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
        String userId = jwtService.extractUserIDFromToken(token);
        user user = userService.getUser(userId);
        stablishment targetStab = stabService.getStab(stablishmentID);

        if(userService.isManager(user)){
            manager stabManager = managerService.getManager(user);
            if(!managerService.isManagerInStab(targetStab, stabManager)){
                throw new ResourceNotFoundException("Manager", "userID", userId, "Establecimiento", "stablishmentID", targetStab.getStablishmentID());
            }
        }

        panicAlertRepo.deleteById(panicAlertId);
    }

    @Transactional(readOnly = true)
    public List<panicAlert> getPanicAlertsByStab(Long stabId, String token) {
        String userId = jwtService.extractUserIDFromToken(token);
        user user = userService.getUser(userId);
        stablishment stab = stabService.getStab(stabId);

        if(userService.isManager(user)){
            manager stabManager = managerService.getManager(user);
            if(!managerService.isManagerInStab(stab, stabManager)){
                throw new ResourceNotFoundException("Manager", "userID", userId, "Establecimiento", "stablishmentID", stab.getStablishmentID());
            }
        }

        return panicAlertRepo.findAllByStablishmentID(stab);
    }

    @Transactional(readOnly = true)
    public List<panicAlert> getPanicAlertsByStabAndUser(Long stabId, String userId, String token) {
        user user = userService.getUser(userId);
        String userToken = jwtService.extractUserIDFromToken(token);
        user userMG = userService.getUser(userToken);
        stablishment stab = stabService.getStab(stabId);

        if(userService.isManager(userMG)){
            manager stabManager = managerService.getManager(userMG);
            if(!managerService.isManagerInStab(stab, stabManager)){
                throw new ResourceNotFoundException("Manager", "userID", userMG, "Establecimiento", "stablishmentID", stab.getStablishmentID());
            }
        }

        return panicAlertRepo.findAllByStablishmentIDAndUserID(stab, user);
    }
}