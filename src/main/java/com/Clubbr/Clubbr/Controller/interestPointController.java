package com.Clubbr.Clubbr.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Service.interestPointService;

import com.Clubbr.Clubbr.Entity.interestPoint;

import java.util.List;

@RestController
@RequestMapping("/stablishment/{stablishmentID}")
public class interestPointController {

    @Autowired
    private interestPointService interestPointService;


    @PostMapping("/interestPoint/add")
    public void addInterestPointToStab(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody interestPoint newInterestPoint){
        interestPointService.addInterestPointToStab(stablishmentID, newInterestPoint);
    }

    @GetMapping("/interestPoint/all")
    public List<interestPoint> getInterestPointsByStablishment(@PathVariable("stablishmentID") Long stablishmentID){
        return interestPointService.getInterestPointsByStablishment(stablishmentID);
    }

    @PostMapping("/events/{eventName}/interestPoint/add")
    public void addInterestPointToEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @RequestBody interestPoint newInterestPoint){
        interestPointService.addInterestPointToEvent(stablishmentID, eventName, newInterestPoint);
    }

    @GetMapping("/events/{eventName}/interestPoint/all")
    public List<interestPoint> getInterestPointsByEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName){
        return interestPointService.getInterestPointsByEventName(eventName, stablishmentID);
    }

    @PutMapping("/interestPoint/update")
    public void updateInterestPointFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody interestPoint targetInterestPoint){
        interestPointService.updateInterestPointFromStablishment(stablishmentID, targetInterestPoint);
    }

    @PutMapping("/events/{eventName}/interestPoint/update")
    public void updateInterestPointFromEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @RequestBody interestPoint targetInterestPoint){
        interestPointService.updateInterestPointFromEvent(stablishmentID, eventName, targetInterestPoint);
    }

    @DeleteMapping("/interestPoint/delete/{interestPointID}")
    public void deleteInterestPointFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("interestPointID") Long interestPointID){
        interestPointService.deleteInterestPointFromStablishment(stablishmentID, interestPointID);
    }

    @DeleteMapping("/events/{eventName}/interestPoint/delete/{interestPointID}")
    public void deleteInterestPointFromEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("interestPointID") Long interestPointID){
        interestPointService.deleteInterestPointFromEvent(stablishmentID, eventName, interestPointID);
    }
}
