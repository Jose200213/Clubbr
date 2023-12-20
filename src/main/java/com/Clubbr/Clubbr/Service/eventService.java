package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.Repository.eventRepo;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Repository.workerRepo;
import com.Clubbr.Clubbr.Repository.userRepo;
import com.Clubbr.Clubbr.advice.ResourceNotFoundException;
import com.Clubbr.Clubbr.config.exception.BadRequestException;
import com.Clubbr.Clubbr.config.exception.NotFoundException;
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
public class eventService {

    @Autowired
    private eventRepo eventRepo;

    @Autowired
    private userRepo userRepo;

    @Autowired
    private stablishmentRepo stabRepo;

    @Autowired

    private MqttClient mqttClient;

    @Autowired
    private jwtService jwtService;


    @Autowired
    private userService userService;


    @Autowired
    private managerService managerService;

    @Autowired
    private stablishmentService stablishmentService;

    @Autowired
    private workerRepo workerRepo;


    @Transactional
    public void addEventToStab(Long stabID, event newEvent, String token) {

        stablishment stab = stablishmentService.getStab(stabID);
        user user = userService.getUser(jwtService.extractUserIDFromToken(token));

        event eventFlag = getEventByStabNameDate(stabID, newEvent.getEventName(), newEvent.getEventDate());

        if (eventFlag != null) {

            throw new BadRequestException("Event with name: " + newEvent.getEventName() + " and date: " + newEvent.getEventDate() + " already exists");

        }

        if (userService.isManager(user)) {
            manager manager = managerService.getManager(user);
            if (!managerService.isManagerInStab(stab, manager)) {
                throw new ResourceNotFoundException("Manager", "userID", user.getUserID(), "Establecimiento", "stablishmentID", stab.getStablishmentID());
            }
        }

        newEvent.setStablishmentID(stab);
        newEvent.setTotalTickets(stab.getCapacity());

        if (newEvent.getInterestPoints() != null) {


            List<interestPoint> iPsToStore = new ArrayList<>();

            for (interestPoint ip : newEvent.getInterestPoints()) {

                interestPoint interestPointAux = new interestPoint();
                interestPointAux.setStablishmentID(stab);
                interestPointAux.setEventName(newEvent);
                interestPointAux.setXCoordinate(ip.getXCoordinate());
                interestPointAux.setYCoordinate(ip.getYCoordinate());
                interestPointAux.setDescription(ip.getDescription());
                iPsToStore.add(interestPointAux);

            }
            newEvent.setInterestPoints(iPsToStore);
        }

        eventRepo.save(newEvent);
    }


    @Transactional(readOnly = true)
    public List<event> getAllEventsOrderedByDateInStab(Long stabID) {
        stablishment stab = stablishmentService.getStab(stabID);
        return eventRepo.findAllByStablishmentIDOrderByEventDateAsc(stab);
    }

    @Transactional(readOnly = true)
    public event getEventByStabNameDate(Long stabID, String name, LocalDate date) {
        stablishment stab = stablishmentService.getStab(stabID);
        return eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, name, date);
    }

    public event getEventByEventNameAndStablishmentID(String eventName, stablishment stablishmentID) {
        return eventRepo.findByEventNameAndStablishmentID(eventName, stablishmentID)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", "eventName", eventName, "Establecimiento", "stablishmentID", stablishmentID.getStablishmentID()));
    }

    @Transactional
    public void updateEventFromStablishment(Long stabID, String eventName, LocalDate eventDate, event targetEvent, String token) {

        stablishment stab = stablishmentService.getStab(stabID);
        event existingEvent = eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, eventName, eventDate);
        user user = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (existingEvent == null) {
            throw new NotFoundException("Event to update not found");
        }

        if (userService.isManager(user)) {
            manager manager = managerService.getManager(user);
            if (!managerService.isManagerInStab(stab, manager)) {
                throw new ResourceNotFoundException("Manager", "userID", user.getUserID(), "Establecimiento", "stablishmentID", stab.getStablishmentID());
            }
        }

        event eventFlag = eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, targetEvent.getEventName(), targetEvent.getEventDate());

        if (eventFlag != null) {
            throw new BadRequestException("Can't update an Event with name: " + targetEvent.getEventName() + " and date: " + targetEvent.getEventDate() + " already exists");
        }

        // Crea un nuevo evento con los datos actualizados
        event newEvent = new event();
        newEvent.setEventName(targetEvent.getEventName());
        newEvent.setEventDate(targetEvent.getEventDate());
        newEvent.setEventFinishDate(targetEvent.getEventFinishDate());
        newEvent.setEventDescription(targetEvent.getEventDescription());
        newEvent.setEventPrice(targetEvent.getEventPrice());
        newEvent.setEventTime(targetEvent.getEventTime());
        newEvent.setTotalTickets(stab.getCapacity());
        newEvent.setStablishmentID(stab);

        // Guarda el nuevo evento
        eventRepo.save(newEvent);

        // Elimina el evento existente
        eventRepo.delete(existingEvent);
    }

    @Transactional
    public void deleteEventFromStablishment(Long stabID, String eventName, LocalDate eventDate, String token) {
        stablishment stab = stablishmentService.getStab(stabID);
        event existingEvent = eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, eventName, eventDate);
        user user = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (existingEvent == null) {
            throw new NotFoundException("Event to delete not found");
        }

        if (userService.isManager(user)) {
            manager manager = managerService.getManager(user);
            if (!managerService.isManagerInStab(stab, manager)) {
                throw new ResourceNotFoundException("Manager", "userID", user.getUserID(), "Establecimiento", "stablishmentID", stab.getStablishmentID());
            }
        }

        eventRepo.delete(existingEvent);
    }

    //////////////////////////////////////////// FUNCION AÑADE EVENTOS PERSISTENTES CON UNA FRECUENCIA PREDETERMINADA DE UNA SEMANA (7 DIAS) No usa Dto////////////////////////////////////////////
    @Transactional
    public void addPersistentEventToStab(Long stabID, int repeticiones, event newEvent, String token) {
        stablishment stab = stablishmentService.getStab(stabID);
        user user = userService.getUser(jwtService.extractUserIDFromToken(token));
        //event eventAux = new event();
        event eventFlag = getEventByStabNameDate(stabID, newEvent.getEventName(), newEvent.getEventDate());

        if (eventFlag != null) {

            throw new BadRequestException("Event with name: " + newEvent.getEventName() + " and date: " + newEvent.getEventDate() + " already exists");

        }

        if (userService.isManager(user)) {
            manager manager = managerService.getManager(user);
            if (!managerService.isManagerInStab(stab, manager)) {
                throw new ResourceNotFoundException("Manager", "userID", user.getUserID(), "Establecimiento", "stablishmentID", stab.getStablishmentID());
            }
        }

        newEvent.setStablishmentID(stab);
        newEvent.setTotalTickets(stab.getCapacity());
        newEvent.setEventTime(stab.getOpenTime().toString());

        int i = 0;

        do {

            eventRepo.save(newEvent);
            newEvent.setEventDate(newEvent.getEventDate().plusDays(7));
            newEvent.setEventFinishDate(newEvent.getEventFinishDate().plusDays(7));
            i++;

        } while (i < repeticiones);

    }

//////////////////////////////////////////// FIN FUNCION AÑADE EVENTOS PERSISTENTES CON UNA FRECUENCIA PREDETERMINADA DE UNA SEMANA (7 DIAS) ////////////////////////////////////////////


    @Transactional
    public void attendanceControlWorkers(Long stabID, String eventName, LocalDate eventDate) throws JsonProcessingException, MqttException {
        List<worker> workers = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        stablishment stab = stabRepo.findById(stabID).orElse(null);
        event existingEvent = eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, eventName, eventDate);

        //workers = workerService.getAllWorkers(stab);

        workers = workerRepo.findAllByStablishmentID(stab);

        // Crear una lista para almacenar los JSON
        List<ObjectNode> jsonList = new ArrayList<>();

        for(worker worker : workers){
            if(worker.getEvent() == existingEvent){
                ObjectNode json = objectMapper.createObjectNode();
                json.put("Date", existingEvent.getEventDate().toString());
                json.put("Time", stab.getOpenTime().toString());
                json.put("StabName", stab.getStabName());
                json.put("StabAddress", stab.getStabAddress());
                json.put("EventName", existingEvent.getEventName());
                json.put("StabId", stab.getStablishmentID());
                json.put("TelegramID", userRepo.findById(worker.getUserID().getUserID()).orElse(null).getTelegramID());


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
            mqttClient.publish("Clubbr/AttendanceControl", mqttMessage);
        } else {
            // Manejar la situación en la que mqttClient es null
            System.err.println("No se puede publicar el mensaje porque el cliente MQTT no está disponible");
        }
    }


}

