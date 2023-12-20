package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.advice.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<user> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

<<<<<<< HEAD
    @GetMapping("/{id}")
    public user getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @PostMapping("/add")
    public void addUser(@RequestBody user newUser) {
        userService.addUser(newUser);
=======
    @GetMapping("/{userID}")
    public ResponseEntity<?> getUser(@PathVariable String userID) {
        try {
            user targetUser = userService.getUser(userID);
            return ResponseEntity.ok(targetUser);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
>>>>>>> PruebaMerge06-12-2023
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody user targetUser) {
        userService.updateUser(targetUser);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

<<<<<<< HEAD

=======
>>>>>>> PruebaMerge06-12-2023
}
