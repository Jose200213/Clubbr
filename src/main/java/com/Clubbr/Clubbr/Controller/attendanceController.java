package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.attendance;
import com.Clubbr.Clubbr.Service.attendanceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/stablishment/{stablishmentID}")
public class attendanceController {

    @Autowired
    private attendanceService attendanceService;


    @PostMapping("/event/{eventName}/{eventDate}/attendance-control")
    public void attendanceControlWorkers(@PathVariable("stablishmentID") Long stabID, @PathVariable("eventName") String eventName, @PathVariable("eventDate") LocalDate eventDate) throws MqttException, JsonProcessingException {
        attendanceService.attendanceControlWorkers(stabID, eventName, eventDate);
    }

    @GetMapping("/event/{eventName}/{eventDate}/workers-attendance")
    public List<attendance> getWorkersAttendanceByEvent(@PathVariable("stablishmentID") Long stabID, @PathVariable("eventName") String eventName, @PathVariable("eventDate") LocalDate eventDate){
        return attendanceService.getWorkersAttendanceByEvent(stabID, eventName, eventDate);
    }

    @GetMapping("/event/{eventName}/{eventDate}/worker/{userID}/attendance")
    public attendance getWorkerAttendanceByEvent(@PathVariable("stablishmentID") Long stabID, @PathVariable("eventName") String eventName, @PathVariable("eventDate") LocalDate eventDate, @PathVariable("userID") String userID){
        return attendanceService.getWorkerAttendanceByEvent(stabID, eventName, eventDate, userID);
    }

    @PutMapping("/event/{eventName}/{eventDate}/worker/{userID}/update-attendance")
    public void updateAttendanceOfWorker(@PathVariable("stablishmentID") Long stabID, @PathVariable("eventName") String eventName, @PathVariable("eventDate") LocalDate eventDate, @PathVariable("userID") String userID, @RequestBody attendance targetAttendance){
        attendanceService.updateAttendanceOfWorker(stabID, eventName, eventDate, userID, targetAttendance);
    }

    @DeleteMapping("/event/{eventName}/{eventDate}/worker/{userID}/delete-attendances")
    public void deleteAttendancesOfWorker(@PathVariable("stablishmentID") Long stabID, @PathVariable("eventName") String eventName, @PathVariable("eventDate") LocalDate eventDate, @PathVariable("userID") String userID){
        attendanceService.deleteAttendancesOfWorker(stabID, eventName, eventDate, userID);
    }

    @DeleteMapping("/event/{eventName}/{eventDate}/delete-attendances")
    public void deleteAttendancesOfEvent(@PathVariable("stablishmentID") Long stabID, @PathVariable("eventName") String eventName, @PathVariable("eventDate") LocalDate eventDate){
        attendanceService.deleteAttendancesOfEvent(stabID, eventName, eventDate);
    }


}
