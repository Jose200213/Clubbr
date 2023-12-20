package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.Service.panicAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Entity.stablishment;
import java.util.List;
import com.Clubbr.Clubbr.Service.stablishmentService;
<<<<<<< HEAD
import com.Clubbr.Clubbr.Service.ticketService;
import com.Clubbr.Clubbr.Service.eventService;
import org.springframework.web.multipart.MultipartFile;
=======
>>>>>>> PruebaMerge06-12-2023

@RestController
@RequestMapping("/stablishment")

public class stablishmentController {

    @Autowired
    private stablishmentService stabService;

<<<<<<< HEAD
    @Autowired
    private eventService eventService;

    @Autowired
    private panicAlertService panicAlertService;

    @GetMapping("/all")
    public List<stablishment> getAllStab() {
        return stabService.getAllStab();
    }

    @GetMapping("/{stablishmentID}")
    public stablishment getStab(@PathVariable Long stablishmentID) { return stabService.getStab(stablishmentID);}

    @PostMapping("/add")
    public void addStab(@RequestBody stablishment newStab) { stabService.addStablishment(newStab); }

    @PutMapping("/update/{stablishmentID}")
    public void updateStab(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody stablishment targetStab) {
        stabService.updateStab(stablishmentID, targetStab);
    }

    @DeleteMapping("/delete/{stablishmentID}")
    public void deleteStab(@PathVariable("stablishmentID") Long stablishmentID) {
        stabService.deleteStab(stablishmentID);
    }
   /*
   @PostMapping("/event/activatePanic")
    public void activatePanic(@RequestBody event targetEvent , @RequestHeader("user-Id") String userId) {

        // Activar el botón de pánico
        panicAlertService.createPanicAlert(targetEvent, userId);
    }
 */


    @PostMapping("/uploadFloorPlan/{stablishmentID}")
    public ResponseEntity<String> uploadFloorPlan(@PathVariable Long stablishmentID, @RequestParam("file") MultipartFile file) {
        try {
            stabService.uploadFloorPlan(stablishmentID, file);
            return ResponseEntity.ok("Plano subido con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir el plano: " + e.getMessage());
=======

    @GetMapping("/all")
    public ResponseEntity<?> getAllStab() {
        try{
            List<stablishment> stablishments = stabService.getAllStab();
            return ResponseEntity.ok(stablishments);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @GetMapping("/{stablishmentID}")
    public ResponseEntity<?> getStab(@PathVariable Long stablishmentID) {
        try{
            stablishment stablishment = stabService.getStab(stablishmentID);
            return ResponseEntity.ok(stablishment);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @GetMapping("/manager/all")
    public ResponseEntity<?> getAllStablishmentFromManager(@RequestHeader("Authorization") String token) {
        try{
            List<stablishment> stablishments = stabService.getAllStablishmentFromManager(token);
            return ResponseEntity.ok(stablishments);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addStab(@RequestBody stablishment newStab) {
        try {
            stabService.addStablishment(newStab);
            return ResponseEntity.ok("Se agregó el establecimiento correctamente ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor al agregar el establecimiento: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateStab(@RequestBody stablishment targetStab, @RequestHeader("Authorization") String token) {
        try {
            stabService.updateStab(targetStab, token);
            return ResponseEntity.ok("Se actualizó el establecimiento correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor al actualizar el establecimiento: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{stablishmentID}")
    public ResponseEntity<String> deleteStab(@PathVariable("stablishmentID") Long stablishmentID, @RequestHeader("Authorization") String token) {
        try {
            stabService.deleteStab(stablishmentID, token);
            return ResponseEntity.ok("Se eliminó el establecimiento correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor al borrar el establecimiento: " + e.getMessage());
>>>>>>> PruebaMerge06-12-2023
        }
    }
}



