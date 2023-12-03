package com.Clubbr.Clubbr.Service;

import java.util.List;

import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.utils.role;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Repository.itemRepo;
import org.springframework.transaction.annotation.Transactional;
import com.Clubbr.Clubbr.Repository.managerRepo;
import com.Clubbr.Clubbr.Repository.userRepo;
import com.Clubbr.Clubbr.Repository.workerRepo;


@Service
public class stablishmentService {

    @Autowired
    private stablishmentRepo stabRepo;

    @Autowired
    private jwtService jwtService;

    @Autowired
    private managerRepo managerRepo;

    @Autowired
    private workerRepo workerRepo;

    @Autowired
    private userRepo userRepo;

    @Transactional(readOnly = true)
    public List<stablishment> getAllStab() { return stabRepo.findAll(); }

    public stablishment getStab(Long stabID) { return stabRepo.findById(stabID).orElse(null);}

    public List<stablishment> getMyManagerStab(String token) {
        user user = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
        manager stabManager = managerRepo.findByUserID(user);
        return stabRepo.findByManagerID(stabManager);
    }

    public void deleteStab(Long stabID, String token) { stabRepo.deleteById(stabID);}

    public void addStablishment(stablishment newStab, String token) {
        user user = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
        manager stabManager = managerRepo.findByUserID(user);

        if (stabManager != null) {
            newStab.setManagerID(stabManager);
            stabManager.getStablishmentID().add(newStab);
            managerRepo.save(stabManager);
            stabRepo.save(newStab);
        }

    }

    public void addWorkerToStab(Long stablishmentID, String userID){
        stablishment targetStab = stabRepo.findById(stablishmentID).orElse(null);
        user targetUser = userRepo.findById(userID).orElse(null);
        worker newWorker = new worker();

        if (targetStab != null && targetUser != null){
            targetUser.setUserRole(role.WORKER);
            newWorker.setUserID(targetUser);
            newWorker.setStablishmentID(targetStab);
            targetStab.getWorkers().add(newWorker);
            stabRepo.save(targetStab);
            workerRepo.save(newWorker);
            userRepo.save(targetUser);
        }
    }

    public void updateStab(Long stablishmentID, stablishment targetStab, String token) {
        stablishment stablishment = stabRepo.findById(stablishmentID).orElse(null);
        user user = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
        manager stabManager = managerRepo.findByUserID(user);

        if (stablishment.getManagerID() == stabManager) {
            stablishment.setCapacity(targetStab.getCapacity());
            stablishment.setStabAddress(targetStab.getStabAddress());
            stablishment.setCloseTime(targetStab.getCloseTime());
            stablishment.setOpenTime(targetStab.getOpenTime());
            stablishment.setStabName(targetStab.getStabName());
            stabRepo.save(stablishment);
        }
    }


}
