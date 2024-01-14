package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.advice.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Service.interestPointService;
import com.Clubbr.Clubbr.Entity.interestPoint;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/stablishment/{stablishmentID}")
public class interestPointController {

    @Autowired
    private interestPointService interestPointService;

    /**
     * This method is used to get an interest point from a stablishment by its ID.
     * @param stablishmentID This is the ID of the stablishment.
     * @param interestPointID This is the ID of the interest point to be retrieved.
     * @return an interest point.
     * @throws ResourceNotFoundException if the stablishment does not exist or if the interest point does not exist.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/interestPoint/{interestPointID}")
    public ResponseEntity<?> getInterestPointByStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("interestPointID") Long interestPointID){
        try {
            interestPoint interestPoint = interestPointService.getInterestPointByStablishment(stablishmentID, interestPointID);
            return ResponseEntity.ok(interestPointService.getInterestPointDto(interestPoint));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to get all interest points from a stablishment.
     * @param stablishmentID This is the ID of the stablishment.
     * @return a list of all interest points from the stablishment.
     * @throws ResourceNotFoundException if the stablishment does not exist or if the stablishment does not have any interest points.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/interestPoint/all")
    public ResponseEntity<?> getInterestPointsByStablishment(@PathVariable("stablishmentID") Long stablishmentID){
        try {
            List<interestPoint> interestPoints = interestPointService.getInterestPointsByStablishment(stablishmentID);
            return ResponseEntity.ok(interestPointService.getInterestPointListDto(interestPoints));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to get an interest point from an event by its ID.
     * @param stablishmentID This is the ID of the stablishment.
     * @param eventName This is the name of the event.
     * @param eventDate This is the date of the event.
     * @param interestPointID This is the ID of the interest point to be retrieved.
     * @return an interest point.
     * @throws ResourceNotFoundException if the stablishment, event or interest point does not exist.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/event/{eventName}/{eventDate}/interestPoint/{interestPointID}")
    public ResponseEntity<?> getInterestPointByEventName(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("eventDate") LocalDate eventDate, @PathVariable("interestPointID") Long interestPointID){
        try {
            interestPoint interestPoint = interestPointService.getInterestPointByEventName(stablishmentID, eventName, eventDate,interestPointID);
            return ResponseEntity.ok(interestPointService.getInterestPointDto(interestPoint));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to get all interest points from an event.
     * @param stablishmentID This is the ID of the stablishment.
     * @param eventName This is the name of the event.
     * @param eventDate This is the date of the event.
     * @return a list of all interest points from the event.
     * @throws ResourceNotFoundException if the stablishment or event does not exist or if the event does not have any interest points.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/event/{eventName}/{eventDate}/interestPoint/all")
    public ResponseEntity<?> getInterestPointsByEventName(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("eventDate") LocalDate eventDate){
        try {
            List<interestPoint> interestPoints = interestPointService.getInterestPointsByEventName(eventName, eventDate, stablishmentID);
            return ResponseEntity.ok(interestPointService.getInterestPointListDto(interestPoints));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to add an interest point to a stablishment.
     * @param stablishmentID This is the ID of the stablishment.
     * @param newInterestPoint This is the interest point to be added.
     *                         <p>The interestPointID can be null as its autogenerated</p>
     * @param token This is the JWT token of the user.
     * @throws ResourceNotFoundException if the stablishment does not exist.
     * @throws Exception if there is an error in the server.
     * @return a message that indicates that the interest point was added successfully.
     * @custom.inputformat {
     *    "interestPointID": "Long",
     *    "description": "String",
     *    "xCoordinate": "float",
     *    "yCoordinate": "float"
     *     }
     */
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

    /**
     * This method is used to add an interest point to an event.
     * @param stablishmentID This is the ID of the stablishment.
     * @param eventName This is the name of the event.
     * @param eventDate This is the date of the event.
     * @param newInterestPoint This is the interest point to be added.
     *                         <p>The interestPointID can be null as its autogenerated</p>
     * @param token This is the JWT token of the user.
     * @throws ResourceNotFoundException if the stablishment or event does not exist.
     * @throws Exception if there is an error in the server.
     * @return a message that indicates that the interest point was added successfully.
     * @custom.inputformat {
     *    "interestPointID": "Long",
     *    "description": "String",
     *    "xCoordinate": "float",
     *    "yCoordinate": "float"
     *     }
     */
    @PostMapping("/event/{eventName}/{eventDate}/interestPoint/add")
    public ResponseEntity<String> addInterestPointToEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("eventDate") LocalDate eventDate, @RequestBody interestPoint newInterestPoint, @RequestHeader("Authorization") String token){
        try {
            interestPointService.addInterestPointToEvent(stablishmentID, eventName, eventDate, newInterestPoint, token);
            return ResponseEntity.ok("Interest Point añadido correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    /**
     * This method is used to update an interest point from a stablishment.
     * @param stablishmentID This is the ID of the stablishment.
     * @param interestPointID This is the ID of the interest point to be updated.
     * @param targetInterestPoint This is the interest point to be updated.
     * @param token This is the JWT token of the user.
     * @throws ResourceNotFoundException if the stablishment or interest point does not exist.
     * @throws Exception if there is an error in the server.
     * @return a message that indicates that the interest point was updated successfully.
     * @custom.inputformat {
     *    "interestPointID": "Long",
     *    "description": "String",
     *    "xCoordinate": "float",
     *    "yCoordinate": "float"
     *     }
     */
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

    /**
     * This method is used to update an interest point from an event.
     * @param stablishmentID This is the ID of the stablishment.
     * @param eventName This is the name of the event.
     * @param eventDate This is the date of the event.
     * @param interestPointID This is the ID of the interest point to be updated.
     * @param targetInterestPoint This is the interest point to be updated.
     * @param token This is the JWT token of the user.
     * @throws ResourceNotFoundException if the stablishment, event or interest point does not exist.
     * @throws Exception if there is an error in the server.
     * @return a message that indicates that the interest point was updated successfully.
     * @custom.inputformat {
     *    "description": "String",
     *    "xCoordinate": "float",
     *    "yCoordinate": "float"
     *     }
     */
    @PutMapping("/event/{eventName}/{eventDate}/interestPoint/update/{interestPointID}")
    public ResponseEntity<String> updateInterestPointFromEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("eventDate") LocalDate eventDate, @PathVariable("interestPointID") Long interestPointID, @RequestBody interestPoint targetInterestPoint, @RequestHeader("Authorization") String token){
        try {
            interestPointService.updateInterestPointFromEvent(stablishmentID, eventName, eventDate, interestPointID, targetInterestPoint, token);
            return ResponseEntity.ok("Interest Point actualizado correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    /**
     * This method is used to delete an interest point from a stablishment.
     * @param stablishmentID This is the ID of the stablishment.
     * @param interestPointID This is the ID of the interest point to be deleted.
     * @param token This is the JWT token of the user.
     * @throws ResourceNotFoundException if the stablishment or interest point does not exist.
     * @throws Exception if there is an error in the server.
     * @return a message that indicates that the interest point was deleted successfully.
     */
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

    /**
     * This method is used to delete an interest point from an event.
     * @param stablishmentID This is the ID of the stablishment.
     * @param eventName This is the name of the event.
     * @param eventDate This is the date of the event.
     * @param interestPointID This is the ID of the interest point to be deleted.
     * @param token This is the JWT token of the user.
     * @throws ResourceNotFoundException if the stablishment, event or interest point does not exist.
     * @throws Exception if there is an error in the server.
     * @return a message that indicates that the interest point was deleted successfully.
     */
    @DeleteMapping("/event/{eventName}/{eventDate}/interestPoint/delete/{interestPointID}")
    public ResponseEntity<String> deleteInterestPointFromEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("eventDate") LocalDate eventDate, @PathVariable("interestPointID") Long interestPointID, @RequestHeader("Authorization") String token){
        try {
            interestPointService.deleteInterestPointFromEvent(stablishmentID, eventName, eventDate, interestPointID, token);
            return ResponseEntity.ok("Interest Point eliminado correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

}
