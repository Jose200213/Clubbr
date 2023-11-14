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

    @GetMapping("/{id}")
    public user getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @PostMapping("/add")
    public void addUser(@RequestBody user newUser) {
        userService.addUser(newUser);
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody user targetUser) {
        userService.updateUser(targetUser);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }


}
