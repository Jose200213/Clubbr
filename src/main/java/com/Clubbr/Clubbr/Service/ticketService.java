package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.ticket;
import com.Clubbr.Clubbr.Entity.user;
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
    private eventRepo eventRepo;

    @Autowired
    private stablishmentRepo stablishmentRepo;

    @Autowired
    private userRepo userRepo;

    @Autowired
    private jwtService jwtService;

    public void addTicketToEvent(Long stablishmentID, String eventName, String token){
        stablishment stablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
        event event = eventRepo.findByEventNameAndStablishmentID(eventName, stablishment);
        String jwtToken = token.substring(7);
        String user = jwtService.extractUserID(jwtToken);
        user userId = userRepo.findById(user).orElse(null);

        if (event != null && stablishment != null && userId != null){
            ticket newTicket = new ticket();
            newTicket.setEventName(event);
            newTicket.setUserID(userId);
            newTicket.setStablishmentID(stablishment);
            newTicket.setTicketPrice(event.getEventPrice());
            userId.getTickets().add(newTicket);
            newTicket.setPurchaseDateTime(LocalDateTime.now());
            ticketRepo.save(newTicket);
        }
    }

    public ticket getTicketFromUser(String token, Long ticketID){
        String jwtToken = token.substring(7);
        String user = jwtService.extractUserID(jwtToken);
        user userId = userRepo.findById(user).orElse(null);

        ticket userTicket = ticketRepo.findById(ticketID).orElse(null);
        if (userId != null && userTicket != null && userTicket.getUserID() == userId){
            return userTicket;
        }
        return null;
    }

    public List<ticket> getAllTicketsFromUser(String token){
        String jwtToken = token.substring(7);
        String user = jwtService.extractUserID(jwtToken);
        user userId = userRepo.findById(user).orElse(null);

        if (userId != null){
            return ticketRepo.findByUserID(userId);
        }
        return null;
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
