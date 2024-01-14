package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.item;
import com.Clubbr.Clubbr.advice.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Service.itemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/stablishment/{stablishmentID}")
public class itemController {

    @Autowired
    private itemService itemService;

    /**
     * This method is used to get all items from a stablishment.
     * @param stablishmentID This is the ID of the stablishment.
     * @param token This is the JWT token of the user.
     * @return a list of all items from the stablishment.
     * @throws ResourceNotFoundException if the stablishment does not exist or if the stablishment does not have any items.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/item/all")
    public ResponseEntity<?> getItemsFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @RequestHeader("Authorization") String token) {
        try {
            List<item> items = itemService.getItemsFromStablishment(stablishmentID, token);
            return ResponseEntity.ok(items);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to get an item from a stablishment by its ID.
     * @param stablishmentID This is the ID of the stablishment.
     * @param itemID This is the ID of the item to be retrieved.
     * @param token This is the JWT token of the user.
     * @return an item.
     * @throws ResourceNotFoundException if the stablishment does not exist or if the item does not exist.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/item/{itemID}")
    public ResponseEntity<?> getItemFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("itemID") Long itemID, @RequestHeader("Authorization") String token) {
        try {
            item item = itemService.getItemFromStablishment(stablishmentID, itemID, token);
            return ResponseEntity.ok(item);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to add an item to a stablishment.
     * @param stablishmentID This is the ID of the stablishment.
     * @param newItem This is the item to be added.
     *                <p>The itemID and itemQuantity can be null.</p>
     *                <p>The itemQuantity will be set to itemStock value.</p>
     *
     * @param token This is the JWT token of the user.
     * @return a message confirming the item was added.
     * @custom.inputformat {
     *     "itemID": "Long",
     *     "itemName": "string",
     *     "itemReference": "string",
     *     "itemStock": "Long",
     *     "itemQuantity": "Long",
     *     "itemPrice": "float",
     *     "itemDistributor": "string"
     * }
     * @throws ResourceNotFoundException if the stablishment does not exist.
     * @throws Exception if there is an error in the server.
     */
    @PostMapping("/item/add")
    public ResponseEntity<String> addItemToStablishment(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody item newItem, @RequestHeader("Authorization") String token) {
        try {
            itemService.addItemToStablishment(stablishmentID, newItem, token);
            return ResponseEntity.ok("Se agregó el item correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor al agregar el item: " + e.getMessage());
        }
    }

    /**
     * This method is used to update an item from a stablishment.
     * @param stablishmentID This is the ID of the stablishment.
     * @param itemID This is the ID of the item to be updated.
     * @param updateItem This is the item to be updated.
     * @param token This is the JWT token of the user.
     * @return a message confirming the item was updated.
     * @custom.inputformat {
     *     "itemID": "Long",
     *     "itemName": "string",
     *     "itemReference": "string",
     *     "itemStock": "Long",
     *     "itemQuantity": "Long",
     *     "itemPrice": "float",
     *     "itemDistributor": "string"
     * }
     * @throws ResourceNotFoundException if the stablishment does not exist or if the item does not exist.
     * @throws Exception if there is an error in the server.
     */
    @PutMapping("/item/update/{itemID}")
    public ResponseEntity<String> updateItemFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("itemID") Long itemID, @RequestBody item updateItem, @RequestHeader("Authorization") String token) {
        try {
            itemService.updateItemFromStablishment(stablishmentID, itemID, updateItem, token);
            return ResponseEntity.ok("Se ha actualizado el item correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor al agregar el item: " + e.getMessage());
        }
    }

    /**
     * This method is used to delete an item from a stablishment.
     * @param stablishmentID This is the ID of the stablishment.
     * @param itemID This is the ID of the item to be deleted.
     * @param token This is the JWT token of the user.
     * @return a message confirming the item was deleted.
     * @throws ResourceNotFoundException if the stablishment does not exist or if the item does not exist.
     * @throws Exception if there is an error in the server.
     */
    @DeleteMapping("/item/delete/{itemID}")
    public ResponseEntity<String> deleteItemFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("itemID") Long itemID, @RequestHeader("Authorization") String token) {
        try {
            itemService.deleteItemFromStablishment(stablishmentID, itemID, token);
            return ResponseEntity.ok("Se ha eliminado el item correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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
