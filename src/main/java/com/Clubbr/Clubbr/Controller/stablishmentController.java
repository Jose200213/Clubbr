package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<stablishment> getAllStablishmentFromManager(@RequestHeader("Authorization") String token) {
        return stabService.getAllStablishmentFromManager(token);
    }

    @PostMapping("/{stablishmentID}/worker/add")
    public void addWorkerToStab(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody worker targetWorker, @RequestHeader("Authorization") String token){
        stabService.addWorkerToStab(stablishmentID, targetWorker, token);
    }

    @PutMapping("/{stablishmentID}/worker/{userID}/interestPoint/{interestPointID}/update")
    public void addWorkerToStabInterestPoint(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("userID") String userID, @PathVariable("interestPointID") Long interestPointID, @RequestHeader("Authorization") String token){
        stabService.addWorkerToStabInterestPoint(stablishmentID, userID, interestPointID, token);
    }

    @PutMapping("/{stablishmentID}/event/{eventName}/worker/{userID}/interestPoint/{interestPointID}/update")
    public void addWorkerToEventInterestPoint(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("userID") String userID, @PathVariable("interestPointID") Long interestPointID, @RequestHeader("Authorization") String token){
        stabService.addWorkerToEventInterestPoint(stablishmentID, eventName, userID, interestPointID, token);
    }

    @PostMapping("/add")
    public void addStab(@RequestBody stablishment newStab, @RequestHeader("Authorization") String token) {
        stabService.addStablishment(newStab, token);
    }

    @PutMapping("/update/{stablishmentID}")
    public void updateStab(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody stablishment targetStab, @RequestHeader("Authorization") String token) {
        stabService.updateStab(stablishmentID, targetStab, token);
    }

    @DeleteMapping("/{stablishmentID}/worker/{userID}/delete")
    public void deleteWorkerFromStab(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("userID") String userID, @RequestHeader("Authorization") String token) {
        stabService.deleteWorkerFromStab(stablishmentID, userID, token);
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


