package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.ticket;
import com.Clubbr.Clubbr.advice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Service.ticketService;

import java.util.Collections;
import java.util.List;

@RestController
public class ticketController {

    @Autowired
    private ticketService ticketService;

    @PostMapping("/stablishment/{stablishmentID}/event/{eventName}/ticket/add")
    public ResponseEntity<String> addTicketToEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        try {
            ticketService.addTicketToEvent(stablishmentID, eventName, token);
            return ResponseEntity.ok("Ticket añadido correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @GetMapping("/ticket/{ticketID}")
    public ResponseEntity<?> getTicketFromUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("ticketID") Long ticketID){
        try {
            return ResponseEntity.ok(ticketService.getTicketFromUser(token, ticketID));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @GetMapping("/ticket/all")
    public ResponseEntity<?> getAllTicketsFromUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            return ResponseEntity.ok(ticketService.getAllTicketsFromUser(token));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    //@Scheduled(cron = "0 0 1 * * ?") // Ejecutar todos los días a la 1 AM
    //@Scheduled(cron = "*/10 * * * * *") // Ejecutar cada minuto TEST
    public void scheduledDeleteExpiredTickets() {
        ticketService.deleteExpiredTickets();
    }
}
