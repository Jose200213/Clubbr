package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Service.jwtService;
import com.Clubbr.Clubbr.advice.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.Service.userService;
import java.util.List;


@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private userService userService;

    @Autowired
    private jwtService jwtService;

    /**
     * This method is used get all users.
     * @return a list of users.
     * @throws ResourceNotFoundException if there are no users.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<user> users = userService.getAllUsers();
            return ResponseEntity.ok(userService.getUserDtoList(users));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to get a user by its ID.
     * @param userID This is the ID of the user.
     * @return the user with the specified ID.
     * @throws ResourceNotFoundException if the user does not exist.
     * @throws Exception if there is an error in the server.
     */
    @GetMapping("/{userID}")
    public ResponseEntity<?> getUser(@PathVariable String userID) {
        try {
            user targetUser = userService.getUser(userID);
            return ResponseEntity.ok(userService.getUserDto(targetUser));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @GetMapping("/myUser")
    public ResponseEntity<?> getMyUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            user targetUser = userService.getUser(jwtService.extractUserIDFromToken(token));
            return ResponseEntity.ok(userService.getUserDto(targetUser));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    /**
     * This method is used to update a user.
     * @param targetUser This is the user that will be updated.
     * @custom.inputformat {
     *    "userID": "string",
     *    "password": "string",
     *    "name": "string",
     *    "surname": "string",
     *    "email": "string",
     *    "phone": "string",
     *    "address": "string",
     *    "country": "string",
     *    "role": "string"
     *    }
     */
    @PutMapping("/update")
    public void updateUser(@RequestBody user targetUser) {
        userService.updateUser(targetUser);
    }

    /**
     * This method is used to delete a user.
     * @param userID This is the ID of the user that will be deleted.
     */
    @DeleteMapping("/delete/{userID}")
    public void deleteUser(@PathVariable String userID) {
        userService.deleteUser(userID);
    }

}
