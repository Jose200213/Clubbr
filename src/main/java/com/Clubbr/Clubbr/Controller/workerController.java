package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.worker;
import com.Clubbr.Clubbr.advice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Service.workerService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/stablishment/{stablishmentID}")
public class workerController {

    @Autowired
    private workerService workerService;

    /**
     * This method is used to get all workers from a stablishment.
     * @param stablishmentID This is the ID of the stablishment.
     * @param token This is the JWT token of the user.
     * @return a list of all workers from a stablishment.
     * @throws ResourceNotFoundException if the stablishment does not exist or does not have any worker.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/worker/all")
    public ResponseEntity<?> getAllWorkersFromStab(@PathVariable("stablishmentID") Long stablishmentID, @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(workerService.getWorkersListDto(workerService.getAllWorkersFromStab(stablishmentID, token)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to get a worker from a stablishment.
     * @param stablishmentID This is the ID of the stablishment.
     * @param userID This is the ID of the user.
     * @param token This is the JWT token of the user.
     * @return a worker.
     * @throws ResourceNotFoundException if the stablishment does not exist or the worker does not exist.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/worker/{userID}")
    public ResponseEntity<?> getWorkerFromStab(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("userID") String userID, @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(workerService.getWorkerDto(workerService.getWorkerFromStab(userID, stablishmentID, token)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to get all workers from an event.
     * @param stablishmentID This is the ID of the stablishment.
     * @param eventName This is the name of the event.
     * @param eventDate This is the date of the event.
     * @param token This is the JWT token of the user.
     * @return a list of all workers from an event.
     * @throws ResourceNotFoundException if the stablishment or the event does not exist or there are no workers in the event.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/event/{eventName}/{eventDate}/worker/all")
    public ResponseEntity<?> getAllWorkersFromEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("eventDate") LocalDate eventDate, @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(workerService.getWorkersListDto(workerService.getAllWorkersFromEvent(stablishmentID, eventName, eventDate, token)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to get a worker from an event.
     * @param stablishmentID This is the ID of the stablishment.
     * @param eventName This is the name of the event.
     * @param eventDate This is the date of the event.
     * @param userID This is the ID of the user.
     * @param token This is the JWT token of the user.
     * @return a worker.
     * @throws ResourceNotFoundException if the stablishment or the event does not exist or the worker does not exist.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/event/{eventName}/{eventDate}/worker/{userID}")
    public ResponseEntity<?> getWorkerFromEvent(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("eventDate") LocalDate eventDate, @PathVariable("userID") String userID, @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(workerService.getWorkerDto(workerService.getWorkerFromEvent(userID, stablishmentID, eventName, eventDate, token)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to add a worker to a stablishment or an event.
     * @param stablishmentID This is the ID of the stablishment.
     * @param targetWorker This is the worker to be added.
     *                     <p>The worker's ID can be null as its autogenerated.</p>
     *                     <p>If the eventID field is null the worker will be added to the stablishment, otherwise will be
     *                     added to the event</p>
     * @param token This is the JWT token of the user.
     * @return a message that indicates the worker has been added successfully.
     * @throws ResourceNotFoundException if the stablishment does not exist.
     * @throws Exception if there is an error in the server.
     * @custom.inputformat {
     *      "id": "Long",
     *     "userID": { "userID": "string"},
     *     "workingHours": "Long",
     *     "salary": "float",
     *     "eventID": { "eventName": "string", "eventDate": "LocalDate" }
     * }
     */
    @PostMapping("/worker/add")
    public ResponseEntity<String> addWorker(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody worker targetWorker, @RequestHeader("Authorization") String token){
        try{
            workerService.addWorkerToStab(stablishmentID, targetWorker, token);
            return ResponseEntity.ok("Se agregó el trabajador correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to add a worker to an event's interest point.
     * @param stablishmentID This is the ID of the stablishment.
     * @param eventName This is the name of the event.
     * @param eventDate This is the date of the event.
     * @param userID This is the ID of the worker's userID.
     * @param interestPointID This is the ID of the interest point.
     * @param token This is the JWT token of the user.
     * @return a message that indicates the worker has been added successfully.
     * @throws ResourceNotFoundException if the stablishment or the event does not exist or the worker does not belong to the event.
     * @throws Exception if there is an error in the server.
     */
    @PutMapping("/event/{eventName}/{eventDate}/worker/{userID}/interestPoint/{interestPointID}/update")
    public ResponseEntity<String> addWorkerToEventInterestPoint(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("eventName") String eventName, @PathVariable("eventDate")LocalDate eventDate, @PathVariable("userID") String userID, @PathVariable("interestPointID") Long interestPointID, @RequestHeader("Authorization") String token){
        try {
            workerService.addWorkerToEventInterestPoint(stablishmentID, eventName, eventDate, userID, interestPointID, token);
            return ResponseEntity.ok("Se agregó el trabajador correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to add a worker to a stablishment's interest point.
     * @param stablishmentID This is the ID of the stablishment.
     * @param userID This is the ID of the worker's userID.
     * @param interestPointID This is the ID of the interest point.
     * @param token This is the JWT token of the user.
     * @return a message that indicates the worker has been added successfully.
     * @throws ResourceNotFoundException if the stablishment does not exist or the worker does not belong to the stablishment.
     * @throws Exception if there is an error in the server.
     */
    @PutMapping("/worker/{userID}/interestPoint/{interestPointID}/update")
    public ResponseEntity<String> addWorkerToStabInterestPoint(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("userID") String userID, @PathVariable("interestPointID") Long interestPointID, @RequestHeader("Authorization") String token){
        try {
            workerService.addWorkerToStabInterestPoint(stablishmentID, userID, interestPointID, token);
            return ResponseEntity.ok("Se agregó el trabajador correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to update a worker.
     * @param stablishmentID This is the ID of the stablishment.
     * @param targetWorker This is the worker to be updated.
     * @param token This is the JWT token of the user.
     * @return a message that indicates the worker has been updated successfully.
     * @throws ResourceNotFoundException if the stablishment or worker does not exist or the worker does not belong to the stablishment.
     * @throws Exception if there is an error in the server.
     * @custom.inputformat {
     *      "id": "Long",
     *     "userID": { "userID": "string"},
     *     "workingHours": "Long",
     *     "salary": "float",
     *     "eventID": { "eventName": "string", "eventDate": "LocalDate" }
     * }
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateWorker(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody worker targetWorker, @RequestHeader("Authorization") String token) {
        try {
            workerService.updateWorker(stablishmentID, targetWorker, token);
            return ResponseEntity.ok("Worker actualizado correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to delete a worker from a stablishment.
     * @param stablishmentID This is the ID of the stablishment.
     * @param userID This is the ID of the worker's userID.
     * @param token This is the JWT token of the user.
     * @return a message that indicates the worker has been deleted successfully.
     * @throws ResourceNotFoundException if the stablishment does not exist or the worker does not belong to the stablishment.
     * @throws Exception if there is an error in the server.
     */
    @DeleteMapping("/worker/{userID}/delete")
    public ResponseEntity<String> deleteWorkerFromStab(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("userID") String userID, @RequestHeader("Authorization") String token) {
        try {
            workerService.deleteWorkerFromStab(stablishmentID, userID, token);
            return ResponseEntity.ok("Se eliminó el trabajador correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }
}
