package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.ticket;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.advice.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Clubbr.Clubbr.Repository.ticketRepo;
import com.Clubbr.Clubbr.Dto.ticketDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", "ticketID", ticketID));

        if (!ticket.getUserID().getUserID().equals(userId.getUserID())){
            throw new ResourceNotFoundException("Ticket", "ticketID", ticketID, "Usuario", "userID", userId.getUserID());
        }
        return ticket;
    }

    public List<ticketDto> getAllTicketsFromUser(String token){
        user userId = userService.getUser(jwtService.extractUserIDFromToken(token));
        List<ticket> tickets = ticketRepo.findByUserID(userId);
        if (tickets.isEmpty()){
            throw new ResourceNotFoundException("Tickets");
        }
        return tickets.stream().map(ticket ->  new ticketDto(ticket)).collect(Collectors.toList());
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
