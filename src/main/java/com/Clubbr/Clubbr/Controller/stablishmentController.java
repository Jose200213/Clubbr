package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Entity.stablishment;
import java.util.List;
import com.Clubbr.Clubbr.Service.stablishmentService;
import com.Clubbr.Clubbr.Service.eventService;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/stablishment")
public class stablishmentController {

    @Autowired
    private stablishmentService stabService;

    @Autowired
    private eventService eventService;

    @GetMapping("/all")
    public List<stablishment> getAllStab() {
        return stabService.getAllStab();
    }

    @GetMapping("/{id}")
    public stablishment getStab(@PathVariable("id") int stabID) { return stabService.getStab(stabID);}

    @PostMapping("/add")
    public void addStab(@RequestBody stablishment newStab) { stabService.addStablishment(newStab);}

    @PutMapping("/update")
    public void updateStab(@RequestBody stablishment targetStab) {
        stabService.updateStab(targetStab);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStab(@PathVariable("id") int stabID) {
        stabService.deleteStab(stabID);
    }


    @PostMapping("/{stablishmentID}/event/add")
    public ResponseEntity<String> addEventToStab(@PathVariable("stablishmentID") int stabID, @RequestBody event newEvent) {
        try{
            eventService.addEventToStab(stabID, newEvent);
            return ResponseEntity.ok("Event added successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la operacion: " + e.getMessage());
        }

        /*@RequestMapping("/events")

        @PostMapping("{id}/addEvent")
        public void addEvent(@PathVariable int stabID){
        ticketService.addTicket
        }*/
    }
}

/*
* {
*   "eventName": "Evento 1",
*   "eventDate": "2020-12-12",
*   "eventFinishDate": "2020-12-13",
*   "eventTime": "22:00",
*   "eventFinishTime": "06:00",
*   "interestPoints": [{}]
* }
*
*
*
* */


