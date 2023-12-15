package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.worker;
import com.Clubbr.Clubbr.advice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Service.workerService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("stablishment/{stablishmentID}")
public class workerController {

    @Autowired
    private workerService workerService;

    @GetMapping("/worker/all")
    public ResponseEntity<List<worker>> getAllWorkers(@PathVariable("stablishmentID") Long stablishmentID, @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(workerService.getAllWorkers(stablishmentID, token));
        } catch (ManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        } catch (ManagerNotFromStablishmentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/worker/{userID}")
    public ResponseEntity<worker> getWorker(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("userID") String userID, @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(workerService.getWorkerFromStab(userID, stablishmentID, token));
        } catch (WorkerNotFoundException | ManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ManagerNotFromStablishmentException | WorkerNotFromStablishmentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/worker/{userID}/delete")
    public ResponseEntity<String> deleteWorkerFromStab(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("userID") String userID, @RequestHeader("Authorization") String token) {
        try {
            workerService.deleteWorkerFromStab(stablishmentID, userID, token);
            return ResponseEntity.ok("Se eliminó el trabajador correctamente");
        } catch (StablishmentNotFoundException | UserNotFoundException | ManagerNotFoundException | WorkerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ManagerNotFromStablishmentException | WorkerNotFromStablishmentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor al eliminar el trabajador");
        }
    }

    @PutMapping("/event/{eventName}/worker/{userID}/interestPoint/{interestPointID}/update")
    public ResponseEntity<String> addWorkerToEventInterestPoint(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("userID") String userID, @PathVariable("interestPointID") Long interestPointID, @RequestHeader("Authorization") String token){
        try {
            workerService.addWorkerToEventInterestPoint(stablishmentID, eventName, userID, interestPointID, token);
            return ResponseEntity.ok("Se agregó el trabajador correctamente");
        } catch (StablishmentNotFoundException | UserNotFoundException | ManagerNotFoundException | WorkerNotFoundException | InterestPointNotFoundException | EventNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ManagerNotFromStablishmentException | WorkerNotFromStablishmentException | InterestPointNotFromEventException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor al agregar el trabajador");
        }
    }

    @PutMapping("/worker/{userID}/interestPoint/{interestPointID}/update")
    public ResponseEntity<String> addWorkerToStabInterestPoint(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("userID") String userID, @PathVariable("interestPointID") Long interestPointID, @RequestHeader("Authorization") String token){
        try {
            workerService.addWorkerToStabInterestPoint(stablishmentID, userID, interestPointID, token);
            return ResponseEntity.ok("Se agregó el trabajador correctamente");
        } catch (StablishmentNotFoundException | UserNotFoundException | ManagerNotFoundException | WorkerNotFoundException | InterestPointNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ManagerNotFromStablishmentException | WorkerNotFromStablishmentException | InterestPointNotFromStablishmentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor al agregar el trabajador");
        }
    }

    @PostMapping("/worker/add")
    public ResponseEntity<String> addWorkerToStab(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody worker targetWorker, @RequestHeader("Authorization") String token){
        try{
            workerService.addWorkerToStab(stablishmentID, targetWorker, token);
            return ResponseEntity.ok("Se agregó el trabajador correctamente");
        } catch (StablishmentNotFoundException | UserNotFoundException | ManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ManagerNotFromStablishmentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor al agregar el trabajador");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateWorker(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody worker targetWorker, @RequestHeader("Authorization") String token) {
        try {
            workerService.updateWorker(stablishmentID, targetWorker, token);
            return ResponseEntity.ok("Worker actualizado correctamente");
        } catch (WorkerNotFoundException | ManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ManagerNotFromStablishmentException | WorkerNotFromStablishmentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
