package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.dto.eventWithPersistenceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Service.eventService;

import java.util.List;

@RestController
@RequestMapping("/stablishment/{stablishmentID}")
public class eventController {

    @Autowired
    private eventService eventService;
    @PostMapping("/event/add")
    public void addEventToStab(@PathVariable("stablishmentID") Long stabID, @RequestBody event newEvent) {
        //try{
        eventService.addEventToStab(stabID, newEvent);
        //return ResponseEntity.ok("Event added successfully");
        //}catch (Exception e){
        //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la operacion: " + e.getMessage());
        //}

    }

    //////////////////////////////////////////// CONTROLADOR AÑADE EVENTOS PERSISTENTES CON DTO ////////////////////////////////////////////
    // Recibe en el json los parametros del evento y ademas el numero de repeticiones (veces que se añadira a DB) y la frecuencia (en dias)
    // Para ello emplea como receptor del json el dto eventWithPersistenceDto.
    @PostMapping("/event/addPersistent")
    public void addPersistentEventToStab(@PathVariable("stablishmentID") Long stabID, @RequestBody eventWithPersistenceDto newEventDto) {
        //try{
        eventService.addPersistentEventToStab(stabID, newEventDto);
        //return ResponseEntity.ok("Event added successfully");
        //}catch (Exception e){
        //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la operacion: " + e.getMessage());
        //}

    }
    //////////////////////////////////////////// FIN CONTROLADOR AÑADE EVENTOS PERSISTENTES CON DTO ////////////////////////////////////////////

    //////////////////////////////////////////// CONTROLADOR ADD EVENTOS PERSISTENTES SIN DTO ////////////////////////////////////////////
    // Asume que la frecuencia es siempre 7 dias. El evento se repetirá cada 7 dias (una semana) tantas veces como repeticiones se indiquen en el path.
    /*@PostMapping("/event/addPersistent/{repeticiones}")
    public void addPersistentEventToStab(@PathVariable("stablishmentID") Long stabID, @PathVariable("repeticiones") int repeticiones, @RequestBody event newEvent) {
        //try{
        eventService.addPersistentEventToStab(stabID, repeticiones, newEvent);
        //return ResponseEntity.ok("Event added successfully");
        //}catch (Exception e){
        //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la operacion: " + e.getMessage());
        //}

    }*/
    //////////////////////////////////////////// FIN CONTROLADOR ADD EVENTOS PERSISTENTES SIN DTO ////////////////////////////////////////////


}
