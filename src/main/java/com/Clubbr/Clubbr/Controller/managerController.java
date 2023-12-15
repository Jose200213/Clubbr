package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.advice.ManagerNotFoundException;
import com.Clubbr.Clubbr.advice.ManagerNotFromStablishmentException;
import com.Clubbr.Clubbr.advice.StablishmentNotFoundException;
import com.Clubbr.Clubbr.advice.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Service.managerService;

@RestController
@RequestMapping("/stablishment/{stablishmentID}/manager")
public class managerController {

    @Autowired
    private managerService managerService;

    @PostMapping("/{userID}/addOwner")
    public ResponseEntity<String> addOwnerManager(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("userID") String userID) {
        try {
            managerService.addOwnerToStab(stablishmentID, userID);
            return ResponseEntity.ok("Manager añadido correctamente");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PostMapping("/{userID}/add")
    public ResponseEntity<String> addManagerToStab(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable String userID, @RequestHeader("Authorization") String token){
        try{
            managerService.addManagerToStab(stablishmentID, userID, token);
            return ResponseEntity.ok("Se agregó el manager correctamente");
        } catch (StablishmentNotFoundException | UserNotFoundException | ManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ManagerNotFromStablishmentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor al agregar el manager: " + e.getMessage());
        }
    }
}
