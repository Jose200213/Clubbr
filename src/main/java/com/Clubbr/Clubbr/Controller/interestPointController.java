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


    //region GET

    // GET ONE BY STAB
    @GetMapping("/interestPoint/{interestPointID}")
    public interestPoint getInterestPointByStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("interestPointID") Long interestPointID){
        return interestPointService.getInterestPointByStablishment(stablishmentID, interestPointID);
    }


    // GET LIST BY STAB
    @GetMapping("/interestPoint/all")
    public List<interestPoint> getInterestPointsByStablishment(@PathVariable("stablishmentID") Long stablishmentID){
        return interestPointService.getInterestPointsByStablishment(stablishmentID);
    }


    // GET ONE BY EVENT
    @GetMapping("/events/{eventName}/interestPoint/{interestPointID}")
    public interestPoint getInterestPointByEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("interestPointID") Long interestPointID){
        return interestPointService.getInterestPointsByEventName(stablishmentID, eventName, interestPointID);
    }


    // GET LIST BY EVENT
    @GetMapping("/events/{eventName}/interestPoint/all")
    public List<interestPoint> getInterestPointsByEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName){
        return interestPointService.getInterestPointsByEventName(eventName, stablishmentID);
    }

    //endregion

    //region POST

    // POST TO STAB
    @PostMapping("/interestPoint/add")
    public void addInterestPointToStab(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody interestPoint newInterestPoint){
        interestPointService.addInterestPointToStab(stablishmentID, newInterestPoint);
    }


    // POST TO EVENT
    @PostMapping("/events/{eventName}/interestPoint/add")
    public void addInterestPointToEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @RequestBody interestPoint newInterestPoint){
        interestPointService.addInterestPointToEvent(stablishmentID, eventName, newInterestPoint);
    }

    //endregion

    //region UPDATE

    // UPDATE FROM STAB
    @PutMapping("/interestPoint/update/{interestPointID}")
    public void updateInterestPointFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("interestPointID") Long interestPointID, @RequestBody interestPoint targetInterestPoint){
        interestPointService.updateInterestPointFromStablishment(stablishmentID, interestPointID, targetInterestPoint);
    }


    // UPDATE FROM EVENT
    @PutMapping("/events/{eventName}/interestPoint/update/{interestPointID}")
    public void updateInterestPointFromEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("interestPointID") Long interestPointID, @RequestBody interestPoint targetInterestPoint){
        interestPointService.updateInterestPointFromEvent(stablishmentID, eventName, interestPointID, targetInterestPoint);
    }

    //endregion

    //region DELETE

    // DELETE FROM STAB
    @DeleteMapping("/interestPoint/delete/{interestPointID}")
    public void deleteInterestPointFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("interestPointID") Long interestPointID){
        interestPointService.deleteInterestPointFromStablishment(stablishmentID, interestPointID);
    }


    // DELETE FROM EVENT
    @DeleteMapping("/events/{eventName}/interestPoint/delete/{interestPointID}")
    public void deleteInterestPointFromEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("interestPointID") Long interestPointID){
        interestPointService.deleteInterestPointFromEvent(stablishmentID, eventName, interestPointID);
    }

    //endregion
}
