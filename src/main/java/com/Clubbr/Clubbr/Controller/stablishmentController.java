package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.advice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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


    @GetMapping("/all")
    public ResponseEntity<List<stablishment>> getAllStab() {
        try{
            List<stablishment> stablishments = stabService.getAllStab();
            return ResponseEntity.ok(stablishments);
        } catch (StablishmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/{stablishmentID}")
    public ResponseEntity<stablishment> getStab(@PathVariable Long stablishmentID) {
        try{
            stablishment stablishment = stabService.getStab(stablishmentID);
            return ResponseEntity.ok(stablishment);
        } catch (StablishmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/manager/all")
    public ResponseEntity<List<stablishment>> getAllStablishmentFromManager(@RequestHeader("Authorization") String token) {
        try{
            List<stablishment> stablishments = stabService.getAllStablishmentFromManager(token);
            return ResponseEntity.ok(stablishments);
        } catch (UserNotFoundException | StablishmentNotFoundException | ManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addStab(@RequestBody stablishment newStab) {
        try {
            stabService.addStablishment(newStab);
            return ResponseEntity.ok("Se agregó el establecimiento correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor al agregar el establecimiento: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateStab(@RequestBody stablishment targetStab, @RequestHeader("Authorization") String token) {
        try {
            stabService.updateStab(targetStab, token);
            return ResponseEntity.ok("Se actualizó el establecimiento correctamente");
        } catch (StablishmentNotFoundException | UserNotFoundException | ManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ManagerNotFromStablishmentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor al actualizar el establecimiento");
        }
    }

    @DeleteMapping("/delete/{stablishmentID}")
    public ResponseEntity<String> deleteStab(@PathVariable("stablishmentID") Long stablishmentID, @RequestHeader("Authorization") String token) {
        try {
            stabService.deleteStab(stablishmentID, token);
            return ResponseEntity.ok("Se eliminó el establecimiento correctamente");
        } catch (StablishmentNotFoundException | UserNotFoundException | ManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ManagerNotFromStablishmentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor al eliminar el establecimiento");
        }
    }
}



