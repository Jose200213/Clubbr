package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Dto.eventDto;
import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Service.eventService;
import com.Clubbr.Clubbr.advice.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController()
public class eventController {

    @Autowired
    private eventService eventService;

    /**
     * This method is used to get all events from a stablishment ordered by date.
     * @param stabID This is the ID of the stablishment.
     * @return a list of all events from the stablishment ordered by date ascendant.
     * @throws ResourceNotFoundException if the stablishment does not exist or if the stablishment does not have any events.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/stablishment/{stablishmentID}/event/all-ordered")
    public ResponseEntity<?> getAllEventsOrderedByDateInStab(@PathVariable("stablishmentID") Long stabID){
        try {
            List<event> events = eventService.getAllEventsOrderedByDateInStab(stabID);
            List<eventDto> eventsListDto = eventService.getEventsListDto(events);
            return ResponseEntity.ok(eventsListDto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to get all events.
     * @return a list of all events.
     * @throws ResourceNotFoundException if there are no events.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/event/all")
    public List<eventDto> getAllEvents(){
        List<event> events =  eventService.getAllEvents();
        return eventService.getEventsListDto(events);
    }

    /**
     * This method is used to get an event from a stablishment by its name and date.
     * @param stablishmentID This is the ID of the stablishment.
     * @param eventName This is the name of the event to be retrieved.
     * @param eventDate This is the date of the event to be retrieved.
     * @return an event.
     * @throws ResourceNotFoundException if the stablishment or event does not exist.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/stablishment/{stablishmentID}/event/{eventName}/{eventDate}")
    public ResponseEntity<?> getEventInStabByEventNameAndDate(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable String eventName, @PathVariable LocalDate eventDate) {
        try {
            event event = eventService.getEventByStabNameDate(stablishmentID, eventName, eventDate);
            eventDto eventDto = eventService.getEventDto(event);
            return ResponseEntity.ok(eventDto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to add an event to a stablishment.
     * @param stabID This is the ID of the stablishment.
     * @param newEvent This is the event to be added.
     * @param token This is the JWT token of the manager.
     * @return a message that indicates the event has been added successfully.
     * @throws ResourceNotFoundException if the stablishment does not exist.
     * @throws Exception if there is an error in the server.
     * @InputFormat: {
     *    "eventName": "String",
     *    "eventDate": "LocalDate",
     *    "eventDescription": "String",
     *    "eventPrice": "float",
     *    "totalTickets": "int",
     *    "eventFinishDate": "LocalDate",
     *    "eventTime": "String"
     *  }
     */
    @PostMapping("/stablishment/{stablishmentID}/event/add")
    public ResponseEntity<String> addEventToStab(@PathVariable("stablishmentID") Long stabID, @RequestBody event newEvent, @RequestHeader("Authorization") String token) {
        try {
            eventService.addEventToStab(stabID, newEvent, token);
            return ResponseEntity.ok("Evento añadido correctamente.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to add a persistent event to a stablishment.
     * @param stabID This is the ID of the stablishment.
     * @param repeticiones This is the number of times the event will be repeated.
     * @param newEvent This is the event to be added.
     * @param token This is the JWT token of the manager.
     * @return a message that indicates the event has been added successfully.
     * @throws ResourceNotFoundException if the stablishment does not exist.
     * @throws Exception if there is an error in the server.
     * @InputFormat: {
     *    "eventName": "String",
     *    "eventDate": "LocalDate",
     *    "eventDescription": "String",
     *    "eventPrice": "float",
     *    "totalTickets": "int",
     *    "eventFinishDate": "LocalDate",
     *    "eventTime": "String"
     *  }
     */
    @PostMapping("/stablishment/{stablishmentID}/event/persistent/{repeticiones}")
    public ResponseEntity<String> addPersistentEventToStab(@PathVariable("stablishmentID") Long stabID, @PathVariable("repeticiones") int repeticiones, @RequestBody event newEvent, @RequestHeader("Authorization") String token) {
        try {
            eventService.addPersistentEventToStab(stabID, repeticiones, newEvent, token);
            return ResponseEntity.ok("Evento Persistente añadido correctamente.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to check the attendance of the event.
     * @param stabID This is the ID of the stablishment.
     * @param eventName This is the name of the event.
     * @param eventDate This is the date of the event.
     * @param token This is the JWT token of the manager.
     * @return a message that indicates that the attendance control has been done successfully.
     * @throws ResourceNotFoundException if the stablishment does not exist or if the stablishment does not have any events.
     * @throws Exception if there is an error in the server.
     */
    @PostMapping("/stablishment/{stablishmentID}/event/{eventName}/{eventDate}/attendance-control")
    public ResponseEntity<String> attendanceControlWorkers(@PathVariable("stablishmentID") Long stabID, @PathVariable("eventName") String eventName, @PathVariable("eventDate") LocalDate eventDate, @RequestHeader("Authorization") String token) throws MqttException, JsonProcessingException {
        try {
            eventService.attendanceControlWorkers(stabID, eventName, eventDate, token);
            return ResponseEntity.ok("Control de asistencia realizado correctamente.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }

    }

    /**
     * This method is used to update an event from a stablishment.
     * @param stablishmentID This is the ID of the stablishment.
     * @param eventName This is the name of the event to be updated.
     * @param eventDate This is the date of the event to be updated.
     * @param targetEvent This is the event to be updated.
     * @param token This is the JWT token of the manager.
     * @return a message that indicates the event has been updated successfully.
     * @throws ResourceNotFoundException if the stablishment or event does not exist.
     * @throws Exception if there is an error in the server.
     * @InputFormat: {
     *    "eventName": "String",
     *    "eventDate": "LocalDate",
     *    "eventDescription": "String",
     *    "eventPrice": "float",
     *    "totalTickets": "int",
     *    "eventFinishDate": "LocalDate",
     *    "eventTime": "String"
     *  }
     */
    @PutMapping("/stablishment/{stablishmentID}/event/{eventName}/{eventDate}/update")
    public ResponseEntity<String> updateEventFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable String eventName, @PathVariable LocalDate eventDate, @RequestBody event targetEvent, @RequestHeader("Authorization") String token) {
        try {
            eventService.updateEventFromStablishment(stablishmentID, eventName, eventDate, targetEvent, token);
            return ResponseEntity.ok("Evento actualizado correctamente.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to delete an event from a stablishment.
     * @param stablishmentID This is the ID of the stablishment.
     * @param eventName This is the name of the event to be deleted.
     * @param eventDate This is the date of the event to be deleted.
     * @param token This is the JWT token of the manager.
     * @return a message that indicates the event has been deleted successfully.
     * @throws ResourceNotFoundException if the stablishment or event does not exist.
     * @throws Exception if there is an error in the server.
     */
    @DeleteMapping("/stablishment/{stablishmentID}/event/{eventName}/{eventDate}/delete")
    public ResponseEntity<String> deleteEventFromStablishment(@PathVariable Long stablishmentID, @PathVariable String eventName, @PathVariable LocalDate eventDate, @RequestHeader("Authorization") String token) {
        try {
            eventService.deleteEventFromStablishment(stablishmentID, eventName, eventDate, token);
            return ResponseEntity.ok("Evento eliminado correctamente.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }
}

