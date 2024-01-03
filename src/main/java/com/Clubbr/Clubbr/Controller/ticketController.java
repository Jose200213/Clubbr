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

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
public class ticketController {

    @Autowired
    private ticketService ticketService;

    /**
     * This method is used to get a ticket from a user by its ID.
     * @param token This is the JWT token of the user.
     * @param ticketID This is the ID of the ticket to be retrieved.
     * @return a ticket.
     * @throws ResourceNotFoundException if the ticket does not exist or if the user does not own the ticket.
     * @throws Exception if there is an error in the server.
     */
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

    /**
     * This method is used to get all tickets from a user.
     * @param token This is the JWT token of the user.
     * @return a list of all tickets from the user.
     * @throws ResourceNotFoundException if the user does not exist or if the user does not own any tickets.
     * @throws Exception if there is an error in the server.
     */
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

    /**
     * This method is used to add a ticket to an event and associate it with a user.
     * @param stablishmentID This is the ID of the stablishment where the event is held.
     * @param eventName This is the name of the event.
     * @param eventDate This is the date of the event.
     * @param token This is the JWT token of the user.
     * @throws ResourceNotFoundException if the stablishment, event or user does not exist.
     * @throws Exception if there is an error in the server.
     */
    @PostMapping("/stablishment/{stablishmentID}/event/{eventName}/{eventDate}/ticket/add")
    public ResponseEntity<String> addTicketToEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("eventDate") LocalDate eventDate, @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        try {
            ticketService.addTicketToEvent(stablishmentID, eventName, eventDate, token);
            return ResponseEntity.ok("Ticket añadido correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    //@Scheduled(cron = "0 0 1 * * ?") // Ejecutar todos los días a la 1 AM
    public void scheduledDeleteExpiredTickets() {
        ticketService.deleteExpiredTickets();
    }
}
