package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Service.paymentService;

import java.time.LocalDate;
import java.util.List;

@RestController
public class paymentController {

    @Autowired
    private paymentService paymentService;


    /**
     * This method is used to get a list of payment by userID.
     * @param userID This is the ID of the user.
     * @return a list of payment.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/payment/user/{userID}")
    public ResponseEntity<?> getPaymentByUserID(@PathVariable("userID") String userID){
        try {
            List<payment> payments = paymentService.getPaymentByUserID(userID);
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * This method is used to get a list of payment by stablishmentID.
     * @param stablishmentID This is the ID of the stablishment.
     * @return a list of payment.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/payment/stablishment/{stablishmentID}")
    public ResponseEntity<?> getPaymentByStab(@PathVariable("stablishmentID") Long stablishmentID){
        try {
            List<payment> payments = paymentService.getPaymentByStab(stablishmentID);
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * This method is used to get a list of payment by event.
     * @param stablishmentID This is the ID of the stablishment.
     * @param eventName This is the name of the event.
     * @param eventDate This is the date of the event.
     * @return a list of payment.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/payment/stablishment/{stablishmentID}/event/{eventName}/{eventDate}")
    public ResponseEntity<?> getPaymentByEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("eventDate") LocalDate eventDate){
        try {
            List<payment> payments = paymentService.getPaymentByEvent(stablishmentID, eventName, eventDate);
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * This method is used to get a payment by its ID.
     * @param paymentID This is the ID of the payment.
     * @return the payment with the specified ID.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/payment/{paymentID}")
    public ResponseEntity<?> getPaymentByID(@PathVariable("paymentID") Long paymentID){
        try {
            payment payment = paymentService.getPaymentById(paymentID);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * This method is used to generate all the payment for a user.
     * @param userID This is the ID of the user.
     * @return a message that indicates the payment has been generated successfully.
     * @throws Exception if there is an error in the server.
     */
    @PostMapping("/payment/generate/user/{userID}")
    public ResponseEntity<?> generatePaymentForUser(@PathVariable("userID") String userID){
        try {
            paymentService.generatePaymentForUser(userID);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * This method is used to generate all the payment for a stablishment.
     * @param stablishmentID This is the ID of the stablishment.
     * @return a message that indicates the payment has been generated successfully.
     * @throws Exception if there is an error in the server.
     */
    @PostMapping("/payment/generate/stablishment/{stablishmentID}")
    public ResponseEntity<?> generatePaymentForStablishment(@PathVariable("stablishmentID") Long stablishmentID, @RequestHeader("Authorization") String token){
        try {
            paymentService.generatePaymentForStablishment(stablishmentID, token);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * This method is used to pay a payment.
     * @param paymentID This is the ID of the payment.
     * @return a message that indicates the payment has been paid successfully.
     * @throws Exception if there is an error in the server.
     */
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
