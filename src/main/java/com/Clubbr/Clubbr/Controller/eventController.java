package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Dto.eventDto;
import com.Clubbr.Clubbr.Dto.eventListDto;
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

    //Controller que devuelve todos los eventos de un local ordenados por fecha de forma ascendente.
    @GetMapping("/stablishment/{stablishmentID}/event/all-ordered")
    public ResponseEntity<?> getAllEventsOrderedByDateInStab(@PathVariable("stablishmentID") Long stabID){
        try {
            List<event> events = eventService.getAllEventsOrderedByDateInStab(stabID);
            List<eventListDto> eventsListDto = eventService.getEventsListDto(events);
            return ResponseEntity.ok(eventsListDto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @GetMapping("/event/all")
    public List<eventListDto> getAllEvents(){
        List<event> events =  eventService.getAllEvents();
        return eventService.getEventsListDto(events);
    }

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
}

