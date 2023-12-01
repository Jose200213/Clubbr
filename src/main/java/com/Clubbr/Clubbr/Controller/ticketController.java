package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Service.ticketService;

import java.util.List;

@RestController
public class ticketController {

    @Autowired
    private ticketService ticketService;

    @PostMapping("/stablishment/{stablishmentID}/event/{eventName}/ticket/add")
    public void addTicketToEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        ticketService.addTicketToEvent(stablishmentID, eventName, token);
    }

    @GetMapping("/ticket/{ticketID}")
    public ticket getTicketFromUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("ticketID") Long ticketID){
        return ticketService.getTicketFromUser(token, ticketID);
    }

    @GetMapping("/ticket/all")
    public List<ticket> getAllTicketsFromUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ticketService.getAllTicketsFromUser(token);
    }

    //@Scheduled(cron = "0 0 1 * * ?") // Ejecutar todos los días a la 1 AM
    //@Scheduled(cron = "*/10 * * * * *") // Ejecutar cada minuto TEST
    public void scheduledDeleteExpiredTickets() {
        ticketService.deleteExpiredTickets();
    }
}
