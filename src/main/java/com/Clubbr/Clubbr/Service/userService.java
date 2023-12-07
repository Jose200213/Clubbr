package com.Clubbr.Clubbr.Service;

import java.util.List;

import com.Clubbr.Clubbr.Entity.manager;
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

    @Autowired
    private managerRepo managerRepo;

    public List<user> getAllUsers() {return userRepo.findAll();
    }
    public user getUser(String id) {return userRepo.findById(id).orElse(null);
    }
    public void addUser(user newUser) {userRepo.save(newUser);
    }
    public void updateUser(user targetUser) {userRepo.save(targetUser);
    }
    public void deleteUser(String id) {userRepo.deleteById(id);
    }

    public void addManager(String userID) {
        user targetUser = userRepo.findById(userID).orElse(null);
        manager targetManager = managerRepo.findByUserID(targetUser);
        if (targetUser != null && targetManager == null){
            manager newManager = new manager();
            targetUser.setUserRole(role.MANAGER);
            newManager.setUserID(targetUser);
            newManager.setOwner(true);

            managerRepo.save(newManager);
            userRepo.save(targetUser);
        }
    }
}
