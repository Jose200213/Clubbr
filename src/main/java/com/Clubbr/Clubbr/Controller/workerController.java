package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.worker;
import com.Clubbr.Clubbr.advice.ManagerNotFoundException;
import com.Clubbr.Clubbr.advice.ManagerNotFromStablishmentException;
import com.Clubbr.Clubbr.advice.UserNotFoundException;
import com.Clubbr.Clubbr.advice.WorkerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Service.workerService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("stablishment/{stablishmentID}/worker")
public class workerController {

    @Autowired
    private workerService workerService;

    @GetMapping("/all")
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


    @GetMapping("/{userID}")
    public ResponseEntity<worker> getWorker(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("userID") String userID, @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(workerService.getWorker(userID, stablishmentID, token));
        } catch (UserNotFoundException | ManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ManagerNotFromStablishmentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateWorker(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody worker targetWorker, @RequestHeader("Authorization") String token) {
        try {
            workerService.updateWorker(stablishmentID, targetWorker, token);
            return ResponseEntity.ok("Worker actualizado correctamente");
        } catch (WorkerNotFoundException | ManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ManagerNotFromStablishmentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
