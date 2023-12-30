package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Dto.stablishmentDto;
import com.Clubbr.Clubbr.Dto.stablishmentListDto;
import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.advice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import com.Clubbr.Clubbr.Service.stablishmentService;

@RestController
@RequestMapping("/stablishment")
public class stablishmentController {

    @Autowired
    private stablishmentService stabService;


    @GetMapping("/all")
    public ResponseEntity<?> getAllStab() {
        try{
            List<stablishmentListDto> stablishments = stabService.getAllStabDto();
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
            stablishmentDto stablishment = stabService.getStabDto(stablishmentID);
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
        }
    }
}
