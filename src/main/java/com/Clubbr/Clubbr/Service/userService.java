package com.Clubbr.Clubbr.Service;

import java.util.ArrayList;
import java.util.List;

import com.Clubbr.Clubbr.Entity.manager;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.advice.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.Repository.userRepo;
import com.Clubbr.Clubbr.Repository.managerRepo;
import com.Clubbr.Clubbr.utils.role;

@Service
public class userService {

    @Autowired
    private userRepo userRepo;

    public List<user> getAllUsers() {return userRepo.findAll();
    }
    public user getUser(String userID) {
        return userRepo.findById(userID).orElseThrow(() -> new UserNotFoundException("No se ha encontrado el usuario con el ID " + userID));
    }

    public void updateUser(user targetUser) {userRepo.save(targetUser);
    }
    public void deleteUser(String id) {userRepo.deleteById(id);
    }

    public boolean isManager(user userID) {
        return userID.getUserRole() == role.MANAGER;
    }
}
