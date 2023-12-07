package com.Clubbr.Clubbr.Controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<user> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userID}")
    public user getUser(@PathVariable String userID) {
        return userService.getUser(userID);
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody user targetUser) {
        userService.updateUser(targetUser);
    }

    @DeleteMapping("/delete/{userID}")
    public void deleteUser(@PathVariable String userID) {
        userService.deleteUser(userID);
    }

    @PostMapping("/manager/{userID}")
    public void addManager(@PathVariable String userID) {
        userService.addManager(userID);
    }
}
