package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.ticket;
import com.Clubbr.Clubbr.Service.ticketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ticket")
public class ticketController {

    @Autowired
    private ticketService tService;

    @GetMapping(value = "/all")
    public List<ticket> getAllTickets() {return tService.getAllTickets();}

    @GetMapping(value = "/{id}")
    public ticket getTicket(@PathVariable int id) {return tService.getTicket(id);}

    @PutMapping(value = "/add")
    public void addTicket(@RequestBody ticket newTicket) {tService.addTicket(newTicket);}

    @PutMapping(value = "/update")
    public void updateTicket(@RequestBody ticket updatedTicket) {tService.updateTicket(updatedTicket);}

    @DeleteMapping(value = "/delete/{id}")
    public void deleteTicket(@PathVariable int id) {tService.deleteTicket(id);}

}
