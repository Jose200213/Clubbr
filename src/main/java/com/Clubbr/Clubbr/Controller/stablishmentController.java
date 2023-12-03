package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.item;
import com.Clubbr.Clubbr.Entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Entity.stablishment;
import java.util.List;
import com.Clubbr.Clubbr.Service.stablishmentService;
import com.Clubbr.Clubbr.Service.ticketService;
import com.Clubbr.Clubbr.Service.eventService;

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

    @GetMapping("/{stablishmentID}")
    public stablishment getStab(@PathVariable Long stablishmentID) { return stabService.getStab(stablishmentID);}

    @GetMapping("/manager/all")
    public List<stablishment> getMyManagerStab(@RequestHeader("Authorization") String token) {
        return stabService.getMyManagerStab(token);
    }

    @PostMapping("/{stablishmentID}/worker/{userID}/add")
    public void addWorkerToStab(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("userID") String userID){
        stabService.addWorkerToStab(stablishmentID, userID);
    }

    @PostMapping("/add")
    public void addStab(@RequestBody stablishment newStab, @RequestHeader("Authorization") String token) {
        stabService.addStablishment(newStab, token);
    }

    @PutMapping("/update/{stablishmentID}")
    public void updateStab(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody stablishment targetStab, @RequestHeader("Authorization") String token) {
        stabService.updateStab(stablishmentID, targetStab, token);
    }

    @DeleteMapping("/delete/{stablishmentID}")
    public void deleteStab(@PathVariable("stablishmentID") Long stablishmentID, @RequestHeader("Authorization") String token) {
        stabService.deleteStab(stablishmentID, token);
    }


    @PostMapping("/{stablishmentID}/event/add")
    public void addEventToStab(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody event newEvent){
        eventService.addEventToStab(stablishmentID, newEvent);
    }

}


