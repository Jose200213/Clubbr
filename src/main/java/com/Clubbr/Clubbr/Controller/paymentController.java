package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Service.paymentService;

import java.util.List;

@RestController
public class paymentController {

    @Autowired
    private paymentService paymentService;

    //TODO: Agregar seguridad a los endpoints

    @PostMapping("/payment/generate/user/{userID}")
    public ResponseEntity<?> generatePaymentForUser(@PathVariable("userID") String userID){
        try {
            paymentService.generatePaymentForUser(userID);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/payment/generate/stablishment/{stablishmentID}")
    public ResponseEntity<?> generatePaymentForStablishment(@PathVariable("stablishmentID") Long stablishmentID, @RequestHeader("Authorization") String token){
        try {
            paymentService.generatePaymentForStablishment(stablishmentID, token);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/payment/user/{userID}")
    public ResponseEntity<?> getPaymentByUserID(@PathVariable("userID") String userID){
        try {
            List<payment> payments = paymentService.getPaymentByUserID(userID);
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/payment/stablishment/{stablishmentID}")
    public ResponseEntity<?> getPaymentByStab(@PathVariable("stablishmentID") Long stablishmentID){
        try {
            List<payment> payments = paymentService.getPaymentByStab(stablishmentID);
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/payment/stablishment/{stablishmentID}/event/{eventName}")
    public ResponseEntity<?> getPaymentByEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName){
        try {
            List<payment> payments = paymentService.getPaymentByEvent(stablishmentID, eventName);
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/payment/{paymentID}")
    public ResponseEntity<?> getPaymentByID(@PathVariable("paymentID") Long paymentID){
        try {
            payment payment = paymentService.getPaymentById(paymentID);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/payment/pay/{paymentID}")
    public ResponseEntity<?> payPayment(@PathVariable("paymentID") Long paymentID){
        try {
            paymentService.payPayment(paymentID);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
