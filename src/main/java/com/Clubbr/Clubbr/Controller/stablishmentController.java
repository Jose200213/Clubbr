package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.Entity.worker;
import com.Clubbr.Clubbr.Service.workerService;
import com.Clubbr.Clubbr.dto.workerDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private workerService workerService;


    @GetMapping("/all")
    public List<stablishment> getAllStab() {
        return stabService.getAllStab();
    }

    @GetMapping("/{stablishmentID}")
    public stablishment getStab(@PathVariable Long stablishmentID) { return stabService.getStab(stablishmentID);}

    @PostMapping("/add")
    public void addStab(@RequestBody stablishment newStab) { stabService.addStablishment(newStab); }

    @PutMapping("/update/{stablishmentID}")
    public void updateStab(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody stablishment targetStab) {
        stabService.updateStab(stablishmentID, targetStab);
    }

    @DeleteMapping("/delete/{stablishmentID}")
    public void deleteStab(@PathVariable("stablishmentID") Long stablishmentID) {
        stabService.deleteStab(stablishmentID);
    }

}



