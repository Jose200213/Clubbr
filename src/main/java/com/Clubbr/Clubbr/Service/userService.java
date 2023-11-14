package com.Clubbr.Clubbr.Service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.Repository.userRepo;

@Service
public class userService {

    @Autowired
    private userRepo userRepo;

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
}
