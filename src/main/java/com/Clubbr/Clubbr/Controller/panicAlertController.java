package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.panicAlert;
import com.Clubbr.Clubbr.Service.panicAlertService;
import com.Clubbr.Clubbr.advice.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stablishment")
public class panicAlertController {

    @Autowired
    private panicAlertService panicAlertService;

    @PostMapping("/event/user/panic-alert/activate")
    public ResponseEntity<String> activatePanic(@RequestBody event targetEvent , @RequestHeader("Authorization") String token) throws JsonProcessingException, MqttException {
        try {
            panicAlertService.createPanicAlert(targetEvent, token);
            return ResponseEntity.ok("Alerta de pánico activada con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al activar la alerta de pánico: " + e.getMessage());
        }
    }

    @DeleteMapping("{stablishmentID}/event/panic-alert/{panicAlertID}/delete")
    public ResponseEntity<String> deletePanicAlertByIdFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("panicAlertID") Long panicAlertID, @RequestHeader("Authorization") String token) {
        try {
            panicAlertService.deletePanicAlertByIdFromStablishment(stablishmentID, panicAlertID, token);
            return ResponseEntity.ok("Alerta de pánico eliminada con éxito");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la alerta de pánico: " + e.getMessage());
        }
    }

    @GetMapping("{stablishmentID}/panic-alerts")
    public ResponseEntity<?> getPanicAlertsByStab(@PathVariable("stablishmentID") Long stabID, @RequestHeader("Authorization") String token) {
        try {
            List<panicAlert> panicAlerts = panicAlertService.getPanicAlertsByStab(stabID, token);
            return ResponseEntity.ok(panicAlerts);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las alertas de pánico: " + e.getMessage());
        }
    }

    @GetMapping("{stablishmentID}/panic-alerts/{userId}")
    public ResponseEntity<?> getPanicAlertsByStabAndUser(@PathVariable("stablishmentID") Long stabID, @PathVariable("userId") String userId, @RequestHeader("Authorization") String token) {
        try {
            List<panicAlert> panicAlerts = panicAlertService.getPanicAlertsByStabAndUser(stabID, userId, token);
            return ResponseEntity.ok(panicAlerts);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las alertas de pánico: " + e.getMessage());
        }
    }
}
