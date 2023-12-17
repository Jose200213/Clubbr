package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.advice.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Service.interestPointService;
import com.Clubbr.Clubbr.Entity.interestPoint;
import java.util.List;

@RestController
@RequestMapping("/stablishment/{stablishmentID}")
public class interestPointController {

    @Autowired
    private interestPointService interestPointService;


    //region GET

    // GET ONE BY STAB
    @GetMapping("/interestPoint/{interestPointID}")
    public ResponseEntity<?> getInterestPointByStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("interestPointID") Long interestPointID){
        try {
            interestPoint interestPoint = interestPointService.getInterestPointByStablishment(stablishmentID, interestPointID);
            return ResponseEntity.ok(interestPoint);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }


    // GET LIST BY STAB
    @GetMapping("/interestPoint/all")
    public ResponseEntity<?> getInterestPointsByStablishment(@PathVariable("stablishmentID") Long stablishmentID){
        try {
            List<interestPoint> interestPoints = interestPointService.getInterestPointsByStablishment(stablishmentID);
            return ResponseEntity.ok(interestPoints);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }


    // GET ONE BY EVENT
    @GetMapping("/event/{eventName}/interestPoint/{interestPointID}")
    public ResponseEntity<?> getInterestPointByEventName(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("interestPointID") Long interestPointID){
        try {
            interestPoint interestPoint = interestPointService.getInterestPointByEventName(stablishmentID, eventName, interestPointID);
            return ResponseEntity.ok(interestPoint);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }


    // GET LIST BY EVENT
    @GetMapping("/event/{eventName}/interestPoint/all")
    public ResponseEntity<?> getInterestPointsByEventName(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName){
        try {
            List<interestPoint> interestPoints = interestPointService.getInterestPointsByEventName(eventName, stablishmentID);
            return ResponseEntity.ok(interestPoints);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    //endregion

    //region POST

    // POST TO STAB
    @PostMapping("/interestPoint/add")
    public ResponseEntity<String> addInterestPointToStab(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody interestPoint newInterestPoint, @RequestHeader("Authorization") String token){
        try {
            interestPointService.addInterestPointToStab(stablishmentID, newInterestPoint, token);
            return ResponseEntity.ok("Interest Point añadido correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }


    // POST TO EVENT
    @PostMapping("/event/{eventName}/interestPoint/add")
    public ResponseEntity<String> addInterestPointToEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @RequestBody interestPoint newInterestPoint, @RequestHeader("Authorization") String token){
        try {
            interestPointService.addInterestPointToEvent(stablishmentID, eventName, newInterestPoint, token);
            return ResponseEntity.ok("Interest Point añadido correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    //endregion

    //region UPDATE

    // UPDATE FROM STAB
    @PutMapping("/interestPoint/update/{interestPointID}")
    public ResponseEntity<String> updateInterestPointFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("interestPointID") Long interestPointID, @RequestBody interestPoint targetInterestPoint, @RequestHeader("Authorization") String token){
        try {
            interestPointService.updateInterestPointFromStablishment(stablishmentID, interestPointID, targetInterestPoint, token);
            return ResponseEntity.ok("Interest Point actualizado correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }  catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }


    // UPDATE FROM EVENT
    @PutMapping("/event/{eventName}/interestPoint/update/{interestPointID}")
    public ResponseEntity<String> updateInterestPointFromEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("interestPointID") Long interestPointID, @RequestBody interestPoint targetInterestPoint, @RequestHeader("Authorization") String token){
        try {
            interestPointService.updateInterestPointFromEvent(stablishmentID, eventName, interestPointID, targetInterestPoint, token);
            return ResponseEntity.ok("Interest Point actualizado correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    //endregion

    //region DELETE

    // DELETE FROM STAB
    @DeleteMapping("/interestPoint/delete/{interestPointID}")
    public ResponseEntity<String> deleteInterestPointFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("interestPointID") Long interestPointID, @RequestHeader("Authorization") String token){
        try {
            interestPointService.deleteInterestPointFromStablishment(stablishmentID, interestPointID, token);
            return ResponseEntity.ok("Interest Point eliminado correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }


    // DELETE FROM EVENT
    @DeleteMapping("/event/{eventName}/interestPoint/delete/{interestPointID}")
    public ResponseEntity<String> deleteInterestPointFromEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("interestPointID") Long interestPointID, @RequestHeader("Authorization") String token){
        try {
            interestPointService.deleteInterestPointFromEvent(stablishmentID, eventName, interestPointID, token);
            return ResponseEntity.ok("Interest Point eliminado correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    //endregion
}
