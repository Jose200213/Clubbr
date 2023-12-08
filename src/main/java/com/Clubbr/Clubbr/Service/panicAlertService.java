package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.*;

import com.Clubbr.Clubbr.Repository.PanicAlertRepo;
import com.Clubbr.Clubbr.Repository.userRepo;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class panicAlertService {

    @Autowired
    private PanicAlertRepo panicAlertRepo;

    @Autowired
    private userRepo userRepo;

    @Autowired
    private stablishmentRepo stablishmentRepo;

    @Transactional
    public void createPanicAlert(event targetEvent, String userId) {
        panicAlert newPanicAlert = new panicAlert();

        user newUser = userRepo.findByUserID(userId);

        newPanicAlert.setEventName(targetEvent);
        newPanicAlert.setUserID(newUser);
        newPanicAlert.setPanicAlertDateTime(LocalDateTime.now());


        panicAlertRepo.save(newPanicAlert);

    }
    @Transactional
    public void deletePanicAlert(Long panicAlertId) {
        panicAlertRepo.deleteById(panicAlertId);
    }




    /*
    public List<panicAlert> getPanicAlerts() {
        return panicAlertRepo.findAll();
    }

    */


    @Transactional(readOnly = true)
    public List<panicAlert> getPanicAlertsByStabAndUser(Long stabID, String userId) {
        stablishment stab = stablishmentRepo.findById(stabID).orElse(null);
        user User = userRepo.findByUserID(userId);
       return panicAlertRepo.findByEventName_StablishmentIDAndUserID(stab, User);
    }

}
