package com.Clubbr.Clubbr.Service;

import java.util.List;
<<<<<<< HEAD
=======

import com.Clubbr.Clubbr.advice.ResourceNotFoundException;
>>>>>>> PruebaMerge06-12-2023
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.Repository.userRepo;
<<<<<<< HEAD
=======
import com.Clubbr.Clubbr.utils.role;
>>>>>>> PruebaMerge06-12-2023

@Service
public class userService {

    @Autowired
    private userRepo userRepo;

<<<<<<< HEAD
    public List<user> getAllUsers() {return userRepo.findAll();
=======
    public List<user> getAllUsers() {
        List<user> users = userRepo.findAll();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("User");
        }
        return users;
>>>>>>> PruebaMerge06-12-2023
    }
    public user getUser(String userID) {
        return userRepo.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User", "userID", userID));
    }
    public void addUser(user newUser) {userRepo.save(newUser);
    }
    public void updateUser(user targetUser) {userRepo.save(targetUser);
    }
    public void deleteUser(String id) {userRepo.deleteById(id);
    }
<<<<<<< HEAD
=======

    public boolean isManager(user userID) {
        return userID.getUserRole() == role.MANAGER;
    }
>>>>>>> PruebaMerge06-12-2023
}
