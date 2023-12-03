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
import com.Clubbr.Clubbr.Repository.interestPointRepo;
import com.Clubbr.Clubbr.Repository.eventRepo;


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

    @Autowired
    private interestPointRepo interestPointRepo;

    @Autowired
    private eventRepo eventRepo;

    @Transactional(readOnly = true)
    public List<stablishment> getAllStab() { return stabRepo.findAll(); }

    public stablishment getStab(Long stabID) { return stabRepo.findById(stabID).orElse(null);}

    public List<stablishment> getAllStablishmentFromManager(String token) {
        user user = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
        manager stabManager = managerRepo.findByUserID(user);
        return stabRepo.findByManagerID(stabManager);
    }

    public void deleteStab(Long stabID, String token) {
        user user = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
        manager stabManager = managerRepo.findByUserID(user);
        stablishment targetStab = stabRepo.findById(stabID).orElse(null);

        if (targetStab != null && targetStab.getManagerID() == stabManager) {
            stabRepo.deleteById(stabID);
        }
    }

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

    public void addWorkerToStab(Long stablishmentID, worker targetWorker, String token){
        stablishment targetStab = stabRepo.findById(stablishmentID).orElse(null);
        manager stabManager = managerRepo.findByUserID(userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null));
        user targetUser = userRepo.findById(targetWorker.getUserID().getUserID()).orElse(null);

        if (targetStab != null && targetUser != null && targetStab.getManagerID() == stabManager) {
            targetUser.setUserRole(role.WORKER);
            targetWorker.setStablishmentID(targetStab);
            targetWorker.setWorkingHours(160L);
            targetStab.getWorkers().add(targetWorker);
            stabRepo.save(targetStab);
            workerRepo.save(targetWorker);
            userRepo.save(targetUser);
        }
    }

    public void addWorkerToStabInterestPoint(Long stablishmentID, String userID, Long interestPointID, String token){
        stablishment targetStab = stabRepo.findById(stablishmentID).orElse(null);
        user targetUser = userRepo.findById(userID).orElse(null);
        manager stabManager = managerRepo.findByUserID(userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null));
        worker worker = workerRepo.findByUserIDAndStablishmentID(targetUser, targetStab);
        interestPoint interestPoint = interestPointRepo.findById(interestPointID).orElse(null);

        if (worker != null && targetStab.getManagerID() == stabManager && interestPoint.getStablishmentID() == targetStab) {
            worker.setInterestPointID(interestPoint);
            interestPoint.getWorkers().add(worker);
            interestPointRepo.save(interestPoint);
            workerRepo.save(worker);
        }
    }

    public void addWorkerToEventInterestPoint(Long stablishmentID, String eventName, String userID, Long interestPointID, String token){
        stablishment targetStab = stabRepo.findById(stablishmentID).orElse(null);
        user targetUser = userRepo.findById(userID).orElse(null);
        manager stabManager = managerRepo.findByUserID(userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null));
        worker worker = workerRepo.findByUserIDAndStablishmentID(targetUser, targetStab);
        event event = eventRepo.findByEventNameAndStablishmentID(eventName, targetStab);
        interestPoint interestPoint = interestPointRepo.findById(interestPointID).orElse(null);

        if (worker != null && targetStab.getManagerID() == stabManager && interestPoint.getEventName() == event && event != null) {
            worker.setInterestPointID(interestPoint);
            interestPoint.getWorkers().add(worker);
            interestPointRepo.save(interestPoint);
            workerRepo.save(worker);
        }
    }

    public void deleteWorkerFromStab(Long stablishmentID, String userID, String token){
        stablishment targetStab = stabRepo.findById(stablishmentID).orElse(null);
        user targetUser = userRepo.findById(userID).orElse(null);
        manager stabManager = managerRepo.findByUserID(userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null));
        worker worker = workerRepo.findByUserIDAndStablishmentID(targetUser, targetStab);

        if (worker != null && targetStab.getManagerID() == stabManager) {
            targetStab.getWorkers().remove(worker);
            stabRepo.save(targetStab);
            workerRepo.delete(worker);
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
