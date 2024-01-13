package com.Clubbr.Clubbr.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.Clubbr.Clubbr.Dto.userDto;
import com.Clubbr.Clubbr.advice.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.Repository.userRepo;
import com.Clubbr.Clubbr.utils.role;

@Service
public class userService {

    @Autowired
    private userRepo userRepo;

    public List<user> getAllUsers() {
        List<user> users = userRepo.findAll();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("User");
        }
        return users;
    }
    public user getUser(String userID) {
        return userRepo.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User", "userID", userID));
    }

    public List<userDto> getUserDtoList(List<user> users) {
        return users.stream().map(userDto::new).collect(Collectors.toList());
    }

    public userDto getUserDto(user user) {
        return new userDto(user);
    }

    public void updateUser(user targetUser) {userRepo.save(targetUser);
    }
    public void deleteUser(String id) {userRepo.deleteById(id);
    }

    public boolean isManager(user userID) {
        return userID.getUserRole() == role.MANAGER;
    }
}
