package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.ticket;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.advice.TicketNotFoundException;
import com.Clubbr.Clubbr.advice.TicketNotFromUserException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Clubbr.Clubbr.Repository.ticketRepo;
import com.Clubbr.Clubbr.Repository.eventRepo;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Repository.userRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

@Service
public class ticketService {

    @Autowired
    private ticketRepo ticketRepo;

    @Autowired
    private eventService eventService;

    @Autowired
    private stablishmentService stablishmentService;

    @Autowired
    private userService userService;

    @Autowired
    private jwtService jwtService;

    public void addTicketToEvent(Long stablishmentID, String eventName, String token){
        stablishment stablishment = stablishmentService.getStab(stablishmentID);
        event event = eventService.getEventByEventNameAndStablishmentID(eventName, stablishment);
        user userId = userService.getUser(jwtService.extractUserIDFromToken(token));

        ticket newTicket = new ticket();
        newTicket.setEventName(event);
        newTicket.setUserID(userId);
        newTicket.setStablishmentID(stablishment);
        newTicket.setTicketPrice(event.getEventPrice());
        userId.getTickets().add(newTicket);
        newTicket.setPurchaseDateTime(LocalDateTime.now());

        ticketRepo.save(newTicket);
    }

    public ticket getTicketFromUser(String token, Long ticketID){
        user userId = userService.getUser(jwtService.extractUserIDFromToken(token));
        ticket ticket = ticketRepo.findById(ticketID)
                .orElseThrow(() -> new TicketNotFoundException("No se ha encontrado el ticket con el ID " + ticketID));

        if (!ticket.getUserID().getUserID().equals(userId.getUserID())){
            throw new TicketNotFromUserException("El ticket con el ID " + ticketID + " no pertenece al usuario con el ID " + userId.getUserID());
        }
        return ticket;
    }

    public List<ticket> getAllTicketsFromUser(String token){
        user userId = userService.getUser(jwtService.extractUserIDFromToken(token));
        return ticketRepo.findByUserID(userId);
    }

    public void deleteExpiredTickets(){
        List<ticket> tickets = ticketRepo.findAll();
        for (ticket ticket : tickets){
            if (ticket.getEventName().getEventDate().isBefore(LocalDate.now())){
                ticketRepo.delete(ticket);
            }
        }
    }
}
