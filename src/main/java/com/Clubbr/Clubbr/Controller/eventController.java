package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Service.eventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/stablishment/{stablishmentID}")
public class eventController {

    @Autowired
    private eventService eventService;

    //Este Controller es llama a una version de añadir evento que permite añadir interest points al evento si y solo si los hay en el body.
    @PostMapping("/event/add")
    public void addEventToStab(@PathVariable("stablishmentID") Long stabID, @RequestBody event newEvent, @RequestHeader("Authorization") String token) {

        eventService.addEventToStab(stabID, newEvent, token);

    }

    //Controller que devuelve todos los eventos de un local ordenados por fecha de forma ascendente.
    @GetMapping("/event/all-ordered")
    public List<event> getAllEventsOrderedByDateInStab(@PathVariable("stablishmentID") Long stabID){
        return eventService.getAllEventsOrderedByDateInStab(stabID);
    }

    //Controller que devuelve un evento de un local por su nombre y fecha.
    @GetMapping("/event/{eventName}/{eventDate}")
    public event getEventInStabByEventNameAndDate(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable String eventName, @PathVariable LocalDate eventDate) {
        return eventService.getEventByStabNameDate(stablishmentID, eventName, eventDate);
    }

    //Controller que maneja la actualizacion de un evento de un local por su nombre y fecha.
    @PutMapping("/event/{eventName}/{eventDate}/update")
    public void updateEventFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable String eventName, @PathVariable LocalDate eventDate, @RequestBody event targetEvent, @RequestHeader("Authorization") String token) {

        eventService.updateEventFromStablishment(stablishmentID, eventName, eventDate, targetEvent, token);

    }

    @DeleteMapping("/event/{eventName}/{eventDate}/delete")
    public void deleteEventFromStablishment(@PathVariable Long stablishmentID, @PathVariable String eventName, @PathVariable LocalDate eventDate, @RequestHeader("Authorization") String token) {


        eventService.deleteEventFromStablishment(stablishmentID, eventName, eventDate, token);

    }


    @PostMapping("/event/persistent/{repeticiones}")
    public void addPersistentEventToStab(@PathVariable("stablishmentID") Long stabID, @PathVariable("repeticiones") int repeticiones, @RequestBody event newEvent, @RequestHeader("Authorization") String token) {


        eventService.addPersistentEventToStab(stabID, repeticiones, newEvent, token);

    }

    @PostMapping("/event/{eventName}/{eventDate}/attendance-control")
    public void attendanceControlWorkers(@PathVariable("stablishmentID") Long stabID, @PathVariable("eventName") String eventName, @PathVariable("eventDate") LocalDate eventDate, @RequestHeader("Authorization") String token) throws MqttException, JsonProcessingException {
        eventService.attendanceControlWorkers(stabID, eventName, eventDate, token);
    }


}

