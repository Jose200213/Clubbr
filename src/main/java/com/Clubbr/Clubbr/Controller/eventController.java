package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.dto.eventWithPersistenceDto;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Service.eventService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/stablishment/{stablishmentID}")
public class eventController {

    @Autowired
    private eventService eventService;

    //Este Controller es llama a una version de añadir evento que permite añadir interest points al evento si y solo si los hay en el body.
    @PostMapping("/event/add")
    public void addEventToStab(@PathVariable("stablishmentID") Long stabID, @RequestBody event newEvent) throws MqttException {

        eventService.addEventToStab(stabID, newEvent);

    }

    //Controller que devuelve todos los eventos de un local ordenados por fecha de forma ascendente.
    @GetMapping("/event/allordered")
    public List<event> getAllEventsOrderedByDateInStab(@PathVariable("stablishmentID") Long stabID){
        return eventService.getAllEventsOrderedByDateInStab(stabID);
    }

    //Controller que devuelve un evento de un local por su nombre y fecha.
    @GetMapping("/event/{eventName}/{eventDate}")
    public event getEventInStabByEventNameAndDate(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable String eventName, @PathVariable LocalDate eventDate) {
        return eventService.getEventByStabNameDate(stablishmentID, eventName, eventDate);
    }

    //Controller que maneja la actualizacion de un evento de un local por su nombre y fecha.
    @PutMapping("/event/update/{eventName}/{eventDate}")
    public void updateEventFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable String eventName, @PathVariable LocalDate eventDate, @RequestBody event targetEvent) {

        eventService.updateEventFromStablishment(stablishmentID, eventName, eventDate, targetEvent);

    }

    @DeleteMapping("/event/delete/{eventName}/{eventDate}")
    public void deleteEventFromStablishment(@PathVariable Long stablishmentID, @PathVariable String eventName, @PathVariable LocalDate eventDate) {

        eventService.deleteEventFromStablishment(stablishmentID, eventName, eventDate);

    }




    //////////////////////////////////////////// CONTROLADOR AÑADE EVENTOS PERSISTENTES CON DTO ////////////////////////////////////////////
    // Recibe en el json los parametros del evento y ademas el numero de repeticiones (veces que se añadira a DB) y la frecuencia (en dias)
    // Para ello emplea como receptor del json el dto eventWithPersistenceDto.
    /*@PostMapping("/event/addPersistent")
    public void addPersistentEventToStab(@PathVariable("stablishmentID") Long stabID, @RequestBody eventWithPersistenceDto newEventDto) {

        eventService.addPersistentEventToStab(stabID, newEventDto);

    }*/
    //////////////////////////////////////////// FIN CONTROLADOR AÑADE EVENTOS PERSISTENTES CON DTO ////////////////////////////////////////////

    //////////////////////////////////////////// CONTROLADOR ADD EVENTOS PERSISTENTES SIN DTO ////////////////////////////////////////////
    // Asume que la frecuencia es siempre 7 dias. El evento se repetirá cada 7 dias (una semana) tantas veces como repeticiones se indiquen en el path.
    @PostMapping("/event/addPersistent/{repeticiones}")
    public void addPersistentEventToStab(@PathVariable("stablishmentID") Long stabID, @PathVariable("repeticiones") int repeticiones, @RequestBody event newEvent) {

        eventService.addPersistentEventToStab(stabID, repeticiones, newEvent);

    }
    //////////////////////////////////////////// FIN CONTROLADOR ADD EVENTOS PERSISTENTES SIN DTO ////////////////////////////////////////////


}

