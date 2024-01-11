package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.advice.*;
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

    /**
     * This method is used to add an owner manager to a stablishment.
     * @param stablishmentID This is the ID of the stablishment.
     * @param userID This is the ID of the user.
     * @return a message that indicates the manager has been added successfully.
     * @throws ResourceNotFoundException if the stablishment does not exist.
     * @throws Exception if there is an error in the server.
     */
    @PostMapping("/{userID}/addOwner")
    public ResponseEntity<String> addOwnerManager(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("userID") String userID) {
        try {
            managerService.addOwnerToStab(stablishmentID, userID);
            return ResponseEntity.ok("Manager añadido correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to add a standard manager to a stablishment.
     * @param stablishmentID This is the ID of the stablishment.
     * @param userID This is the ID of the user.
     * @param token This is the JWT token of the user.
     * @return a message that indicates the manager has been added successfully.
     * @throws ResourceNotFoundException if the stablishment does not exist.
     * @throws Exception if there is an error in the server.
     */
    @PostMapping("/{userID}/add")
    public ResponseEntity<String> addManagerToStab(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable String userID, @RequestHeader("Authorization") String token){
        try{
            managerService.addManagerToStab(stablishmentID, userID, token);
            return ResponseEntity.ok("Se agregó el manager correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor al agregar el manager: " + e.getMessage());
        }
    }
}
