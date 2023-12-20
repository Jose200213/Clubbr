package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.event;
<<<<<<< HEAD
import com.Clubbr.Clubbr.Entity.panicAlert;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.Entity.worker;
import com.Clubbr.Clubbr.dto.eventWithPersistenceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
=======
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> PruebaMerge06-12-2023
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Service.eventService;
import com.Clubbr.Clubbr.Service.panicAlertService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/stablishment/{stablishmentID}")
public class eventController {


    @Autowired
    private eventService eventService;

    @Autowired
    private panicAlertService panicAlertService;


    //Este Controller es llama a una version de añadir evento que permite añadir interest points al evento si y solo si los hay en el body.
    @PostMapping("/event/add")
    public void addEventToStab(@PathVariable("stablishmentID") Long stabID, @RequestBody event newEvent, @RequestHeader("Authorization") String token) {

        eventService.addEventToStab(stabID, newEvent, token);

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
<<<<<<< HEAD
    public ResponseEntity<String> updateEventFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable String eventName, @PathVariable LocalDate eventDate, @RequestBody event targetEvent) {
        try {
            eventService.updateEventFromStablishment(stablishmentID, eventName, eventDate, targetEvent);
            return ResponseEntity.ok("Operación exitosa"); // O cualquier mensaje que desees
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la operación: " + e.getMessage());
        }
=======
    public void updateEventFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable String eventName, @PathVariable LocalDate eventDate, @RequestBody event targetEvent, @RequestHeader("Authorization") String token) {

        eventService.updateEventFromStablishment(stablishmentID, eventName, eventDate, targetEvent, token);
>>>>>>> PruebaMerge06-12-2023

    }

    @DeleteMapping("/event/delete/{eventName}/{eventDate}")
    public void deleteEventFromStablishment(@PathVariable Long stablishmentID, @PathVariable String eventName, @PathVariable LocalDate eventDate, @RequestHeader("Authorization") String token) {

        eventService.deleteEventFromStablishment(stablishmentID, eventName, eventDate, token);

    }


    //////////////////////////////////////////// CONTROLADOR AÑADE EVENTOS PERSISTENTES CON DTO ////////////////////////////////////////////
    // Recibe en el json los parametros del evento y ademas el numero de repeticiones (veces que se añadira a DB) y la frecuencia (en dias)
    // Para ello emplea como receptor del json el dto eventWithPersistenceDto.
    @PostMapping("/event/addPersistent")
    public void addPersistentEventToStab(@PathVariable("stablishmentID") Long stabID, @RequestBody eventWithPersistenceDto newEventDto) {

        eventService.addPersistentEventToStab(stabID, newEventDto);

    }
    //////////////////////////////////////////// FIN CONTROLADOR AÑADE EVENTOS PERSISTENTES CON DTO ////////////////////////////////////////////

    //////////////////////////////////////////// CONTROLADOR ADD EVENTOS PERSISTENTES SIN DTO ////////////////////////////////////////////
    // Asume que la frecuencia es siempre 7 dias. El evento se repetirá cada 7 dias (una semana) tantas veces como repeticiones se indiquen en el path.
<<<<<<< HEAD
    /*@PostMapping("/event/addPersistent/{repeticiones}")
    public void addPersistentEventToStab(@PathVariable("stablishmentID") Long stabID, @PathVariable("repeticiones") int repeticiones, @RequestBody event newEvent) {
        //try{
        eventService.addPersistentEventToStab(stabID, repeticiones, newEvent);
        //return ResponseEntity.ok("Event added successfully");
        //}catch (Exception e){
        //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la operacion: " + e.getMessage());
        //}
=======
    @PostMapping("/event/addPersistent/{repeticiones}")
    public void addPersistentEventToStab(@PathVariable("stablishmentID") Long stabID, @PathVariable("repeticiones") int repeticiones, @RequestBody event newEvent, @RequestHeader("Authorization") String token) {

        eventService.addPersistentEventToStab(stabID, repeticiones, newEvent, token);
>>>>>>> PruebaMerge06-12-2023

    }*/
    //////////////////////////////////////////// FIN CONTROLADOR ADD EVENTOS PERSISTENTES SIN DTO ////////////////////////////////////////////

    @PostMapping("/event/user/activatePanic")
    public ResponseEntity<String> activatePanic(@RequestBody event targetEvent , @RequestHeader("user-Id") String userId) {
        try {
            // Activar el botón de pánico
            panicAlertService.createPanicAlert(targetEvent, userId);
            return ResponseEntity.ok("Alerta de pánico activada con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al activar la alerta de pánico: " + e.getMessage());
        }
    }

    @DeleteMapping("event/panicAlert/{panicAlertId}")
    public ResponseEntity<String> deletePanicAlert(@PathVariable Long panicAlertId) {
        try {
            panicAlertService.deletePanicAlert(panicAlertId);
            return ResponseEntity.ok("Alerta de pánico eliminada con éxito");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la alerta de pánico: " + e.getMessage());
        }
    }

    /*
    @GetMapping("/event/panicAlerts")
    public List<panicAlert> getPanicAlerts() {
        return panicAlertService.getPanicAlerts();
    }
    */

    @GetMapping("/event/panicAlerts/{userId}")
    public List<panicAlert> getPanicAlertsByStabAndUser(@PathVariable("stablishmentID") Long stabID, @PathVariable("userId") String userId) {
        return panicAlertService.getPanicAlertsByStabAndUser(stabID, userId);
    }



}
