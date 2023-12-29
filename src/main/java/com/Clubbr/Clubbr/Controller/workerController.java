package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.worker;
import com.Clubbr.Clubbr.advice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Service.workerService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/stablishment/{stablishmentID}")
public class workerController {

    @Autowired
    private workerService workerService;

    @GetMapping("/worker/all")
    public ResponseEntity<?> getAllWorkers(@PathVariable("stablishmentID") Long stablishmentID, @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(workerService.getAllWorkersFromStab(stablishmentID, token));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }


    @GetMapping("/worker/{userID}")
    public ResponseEntity<?> getWorker(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("userID") String userID, @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(workerService.getWorkerFromStab(userID, stablishmentID, token));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @DeleteMapping("/worker/{userID}/delete")
    public ResponseEntity<String> deleteWorkerFromStab(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("userID") String userID, @RequestHeader("Authorization") String token) {
        try {
            workerService.deleteWorkerFromStab(stablishmentID, userID, token);
            return ResponseEntity.ok("Se eliminó el trabajador correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PutMapping("/event/{eventName}/{eventDate}/worker/{userID}/interestPoint/{interestPointID}/update")
    public ResponseEntity<String> addWorkerToEventInterestPoint(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("eventDate")LocalDate eventDate, @PathVariable("userID") String userID, @PathVariable("interestPointID") Long interestPointID, @RequestHeader("Authorization") String token){
        try {
            workerService.addWorkerToEventInterestPoint(stablishmentID, eventName, eventDate, userID, interestPointID, token);
            return ResponseEntity.ok("Se agregó el trabajador correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PutMapping("/worker/{userID}/interestPoint/{interestPointID}/update")
    public ResponseEntity<String> addWorkerToStabInterestPoint(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("userID") String userID, @PathVariable("interestPointID") Long interestPointID, @RequestHeader("Authorization") String token){
        try {
            workerService.addWorkerToStabInterestPoint(stablishmentID, userID, interestPointID, token);
            return ResponseEntity.ok("Se agregó el trabajador correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PostMapping("/worker/add")
    public ResponseEntity<String> addWorkerToStab(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody worker targetWorker, @RequestHeader("Authorization") String token){
        try{
            workerService.addWorkerToStab(stablishmentID, targetWorker, token);
            return ResponseEntity.ok("Se agregó el trabajador correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateWorker(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody worker targetWorker, @RequestHeader("Authorization") String token) {
        try {
            workerService.updateWorker(stablishmentID, targetWorker, token);
            return ResponseEntity.ok("Worker actualizado correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }
}
