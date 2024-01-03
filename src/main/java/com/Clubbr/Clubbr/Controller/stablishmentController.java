package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Dto.stablishmentDto;
import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.advice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.Clubbr.Clubbr.Service.stablishmentService;

@RestController
@RequestMapping("/stablishment")
public class stablishmentController {

    @Autowired
    private stablishmentService stabService;


    /**
     * This method is used to get all stablishments.
     * @return a list of all stablishments.
     * @throws ResourceNotFoundException if there are no stablishments in the database.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllStab() {
        try{
            List<stablishmentDto> stablishments = stabService.getAllStabDto();
            return ResponseEntity.ok(stablishments);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to get a stablishment by its ID.
     * @param stablishmentID This is the ID of the stablishment to be retrieved.
     * @return a stablishment.
     * @throws ResourceNotFoundException if the stablishment does not exist.
     * @throws Exception if there is an error in the server.
     */
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

    /**
     * This method is used to get all stablishments from a manager.
     * @param token This is the JWT token of the manager.
     * @return a list of all stablishments from the manager.
     * @throws ResourceNotFoundException if the manager does not exist
     *                                  or if the manager does not have any stablishments.
     * @throws Exception if there is an error in the server.
     */
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

    /**
     * This method is used to add a new stablishment.
     * @param newStab This is a json that contains the new stablishment's details.
     *                <p>The stablishment's ID can be null as it generates automatically.</p>
     * @return a message that confirms that the stablishment was added successfully.
     * @throws Exception if there is an error in the server.
     * @InputFormat: {
     *     "stablishmentID": "Long",
     *     "stabAddress": "string",
     *     "stabName": "string",
     *     "openTime": "LocalTime",
     *     "closeTime": "LocalTime",
     *     "capacity": "int"
     *  }
     */
    @PostMapping("/add")
    public ResponseEntity<String> addStab(@RequestBody stablishment newStab) {
        try {
            stabService.addStablishment(newStab);
            return ResponseEntity.ok("Se agregó el establecimiento correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor al agregar el establecimiento: " + e.getMessage());
        }
    }

    /**
     * This method is used to update a stablishment.
     * @param targetStab This is a json that contains the stablishment's details.
     * @param token This is the JWT token of the manager.
     * @return a message that confirms that the stablishment was updated successfully.
     * @throws ResourceNotFoundException if the stablishment does not exist or if the manager does not own the stablishment.
     * @throws Exception if there is an error in the server.
     * @InputFormat: {
     *     "stablishmentID": "Long",
     *     "stabAddress": "string",
     *     "stabName": "string",
     *     "openTime": "LocalTime",
     *     "closeTime": "LocalTime",
     *     "capacity": "int"
     *  }
     */
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

    /**
     * This method is used to delete a stablishment.
     * @param stablishmentID This is the ID of the stablishment to be deleted.
     * @param token This is the JWT token of the manager.
     * @return a message that confirms that the stablishment was deleted successfully.
     * @throws ResourceNotFoundException if the stablishment does not exist or if the manager does not own the stablishment.
     * @throws Exception if there is an error in the server.
     */
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
