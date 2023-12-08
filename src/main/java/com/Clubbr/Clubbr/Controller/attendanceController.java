package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.panicAlert;
import com.Clubbr.Clubbr.dto.eventWithPersistenceDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Service.eventService;
import com.Clubbr.Clubbr.Service.panicAlertService;
import com.Clubbr.Clubbr.Service.attendanceService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/stablishment/{stablishmentID}")
public class attendanceController {

    @Autowired
    private attendanceService attendanceService;


    @PostMapping("/event/{eventName}/{eventDate}/attendanceControl")
    public void attendanceControlWorkers(@PathVariable("stablishmentID") Long stabID, @PathVariable("eventName") String eventName, @PathVariable("eventDate") LocalDate eventDate) throws MqttException, JsonProcessingException {
        attendanceService.attendanceControlWorkers(stabID, eventName, eventDate);
    }

}
