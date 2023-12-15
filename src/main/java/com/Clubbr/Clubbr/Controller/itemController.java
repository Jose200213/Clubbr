package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.item;
import com.Clubbr.Clubbr.advice.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Service.itemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/stablishment/{stablishmentID}")
public class itemController {

    @Autowired
    private itemService itemService;

    @PostMapping("/item/add")
    public ResponseEntity<String> addItemToStablishment(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody item newItem, @RequestHeader("Authorization") String token) {
        try {
            itemService.addItemToStablishment(stablishmentID, newItem, token);
            return ResponseEntity.ok("Se agregó el item correctamente");
        } catch (UserNotFoundException | StablishmentNotFoundException | ManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ManagerNotFromStablishmentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor al agregar el item: " + e.getMessage());
        }
    }

    @GetMapping("/item/all")
    public ResponseEntity<List<item>> getItemsFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @RequestHeader("Authorization") String token) {
        try {
            List<item> items = itemService.getItemsFromStablishment(stablishmentID, token);
            return ResponseEntity.ok(items);
        } catch (StablishmentNotFoundException | UserNotFoundException | ManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        } catch (ManagerNotFromStablishmentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/item/{itemID}")
    public ResponseEntity<item> getItemFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("itemID") Long itemID, @RequestHeader("Authorization") String token) {
        try {
            item item = itemService.getItemFromStablishment(stablishmentID, itemID, token);
            return ResponseEntity.ok(item);
        } catch (StablishmentNotFoundException | ItemNotFoundException | ManagerNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ManagerNotFromStablishmentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/item/update/{itemID}")
    public ResponseEntity<String> updateItemFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("itemID") Long itemID, @RequestBody item updateItem, @RequestHeader("Authorization") String token) {
        try {
            itemService.updateItemFromStablishment(stablishmentID, itemID, updateItem, token);
            return ResponseEntity.ok("Se ha actualizado el item correctamente");
        } catch (StablishmentNotFoundException | ItemNotFoundException | UserNotFoundException | ManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ManagerNotFromStablishmentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor al agregar el item: " + e.getMessage());
        }
    }

    @DeleteMapping("/item/delete/{itemID}")
    public ResponseEntity<String> deleteItemFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("itemID") Long itemID, @RequestHeader("Authorization") String token) {
        try {
            itemService.deleteItemFromStablishment(stablishmentID, itemID, token);
            return ResponseEntity.ok("Se ha eliminado el item correctamente");
        } catch (StablishmentNotFoundException | ItemNotFoundException | UserNotFoundException | ManagerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ManagerNotFromStablishmentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor al agregar el item: " + e.getMessage());
        }
    }

    // Exception handler for unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor al procesar la solicitud");
    }
}
