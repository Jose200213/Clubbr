package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.panicAlert;
import com.Clubbr.Clubbr.Service.panicAlertService;
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
    public ResponseEntity<String> activatePanic(@RequestBody event targetEvent , @RequestHeader("user-Id") String userId) throws JsonProcessingException, MqttException {
        try {
            // Activar el botón de pánico
            panicAlertService.createPanicAlert(targetEvent, userId);
            return ResponseEntity.ok("Alerta de pánico activada con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al activar la alerta de pánico: " + e.getMessage());
        }
    }

    @DeleteMapping("event/panic-alert/{panicAlertId}/delete")
    public ResponseEntity<String> deletePanicAlertById(@PathVariable("panicAlertId") Long panicAlertId) {
        try {
            panicAlertService.deletePanicAlertById(panicAlertId);
            return ResponseEntity.ok("Alerta de pánico eliminada con éxito");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la alerta de pánico: " + e.getMessage());
        }
    }

    @GetMapping("{stablishmentID}/panic-alerts")
    public List<panicAlert> getPanicAlertsByStab(@PathVariable("stablishmentID") Long stabID) {
        return panicAlertService.getPanicAlertsByStab(stabID);
    }
}
