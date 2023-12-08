package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.*;

import com.Clubbr.Clubbr.Repository.panicAlertRepo;
import com.Clubbr.Clubbr.Service.workerService;
import com.Clubbr.Clubbr.Repository.eventRepo;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
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
    private MqttClient mqttClient;

    @Autowired
    private userRepo userRepo;

    @Autowired
    private stablishmentRepo stabRepo;

    @Autowired
    private eventRepo eventRepo;

    @Autowired
    private workerService workerService;


    @Transactional
    public void createPanicAlert(event targetEvent, String userId) throws JsonProcessingException, MqttException {
        panicAlert newPanicAlert = new panicAlert();
        user alertUser = userRepo.findByUserID(userId);

        newPanicAlert.setEventName(targetEvent);
        newPanicAlert.setStablishmentID(targetEvent.getStablishmentID());
        newPanicAlert.setUserID(alertUser);
        newPanicAlert.setPanicAlertDateTime(LocalDateTime.now());

        List<worker> workers = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        stablishment stab = stabRepo.findById(targetEvent.getStablishmentID().getStablishmentID()).orElse(null);
        event existingEvent = eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, targetEvent.getEventName(), targetEvent.getEventDate());

        workers = workerService.getAllWorkers(stab);

        // Crear una lista para almacenar los JSON
        List<ObjectNode> jsonList = new ArrayList<>();


        // Formatear la hora, minutos y segundos sin nanosegundos
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");


        for(worker worker : workers){
            if(worker.getEventName() == existingEvent || worker.getEventName() == null){
                ObjectNode json = objectMapper.createObjectNode();
                json.put("Date", LocalDate.now().toString());
                json.put("Time", LocalTime.now().format(formatter).toString());
                json.put("StabName", stab.getStabName());
                json.put("EventName", existingEvent.getEventName());
                json.put("UserName", alertUser.getName());
                json.put("UserSurname", alertUser.getSurname());
                json.put("TelegramID", userRepo.findById(worker.getUserID().getUserID()).orElse(null).getTelegramID());

                // Añadir el JSON a la lista
                jsonList.add(json);
            }
        }

        // Convertir la lista de JSON a una cadena de texto JSON
        String jsonString = objectMapper.writeValueAsString(jsonList);

        // Publicar la cadena de texto JSON como un mensaje MQTT
        byte[] payload = jsonString.getBytes();
        MqttMessage mqttMessage = new MqttMessage(payload);
        //mqttClient.publish("Clubbr/AttendanceControl", mqttMessage);
        if (mqttClient != null) {
            mqttClient.publish("Clubbr/PanicAlert", mqttMessage);
        } else {
            // Manejar la situación en la que mqttClient es null
            System.err.println("No se puede publicar el mensaje porque el cliente MQTT no está disponible");
        }

        panicAlertRepo.save(newPanicAlert);

    }

    @Transactional
    public void deletePanicAlertById(Long panicAlertId) {

        panicAlertRepo.deleteById(panicAlertId);
    }

    @Transactional(readOnly = true)
    public List<panicAlert> getPanicAlertsByStab(Long stabId) {
        stablishment stab = stabRepo.findById(stabId).orElse(null);
        return panicAlertRepo.findAllByStablishmentID(stab);
    }
}