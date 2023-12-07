package com.Clubbr.Clubbr.Service;

import java.util.ArrayList;
import java.util.List;

import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.advice.*;
import com.Clubbr.Clubbr.utils.role;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
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
    public List<stablishment> getAllStab() {
        List<stablishment> stablishments = stabRepo.findAll();

        if (stablishments.isEmpty()) {
            throw new StablishmentNotFoundException("No se han encontrado establecimientos");
        }
        return stablishments;
    }


    public stablishment getStab(Long stabID) {
        stablishment stablishment = stabRepo.findById(stabID).orElse(null);

        if (stablishment == null) {
            throw new StablishmentNotFoundException("No se ha encontrado el establecimiento con el ID " + stabID);
        }
        return stablishment;
    }

    public List<stablishment> getAllStablishmentFromManager(String token) {
        user user = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);

        if (user == null) {
            throw new UserNotFoundException("No se ha encontrado el usuario con el ID " + jwtService.extractUserIDFromToken(token));
        }
        manager stabManager = managerRepo.findByUserID(user);

        if (stabManager == null) {
            throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + jwtService.extractUserIDFromToken(token));
        }

        List<stablishment> stablishments = stabRepo.findByManagerID(stabManager);
        if (stablishments.isEmpty()) {
            throw new StablishmentNotFoundException("No se han encontrado establecimientos asociados al manager con el ID " + jwtService.extractUserIDFromToken(token));
        }
        return stablishments;
    }

    public void deleteStab(Long stabID, String token) {
        user user = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("No se ha encontrado el usuario con el ID " + jwtService.extractUserIDFromToken(token));
        }

        manager stabManager = managerRepo.findByUserID(user);
        if (stabManager == null) {
            throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + jwtService.extractUserIDFromToken(token));
        }

        stablishment targetStab = stabRepo.findById(stabID).orElse(null);

        if (targetStab == null) {
            throw new StablishmentNotFoundException("No se ha encontrado el establecimiento con el ID " + stabID);
        }else if (!isManagerInStab(targetStab, stabManager)) {
            throw new ManagerNotFromStablishmentException("El establecimiento con el ID " + stabID + " no pertenece al manager con el ID " + jwtService.extractUserIDFromToken(token));
        }

        stabManager.getStablishmentID().remove(targetStab);
        managerRepo.save(stabManager);
        stabRepo.deleteById(stabID);
    }

    public void addStablishment(stablishment newStab, String token) {
        user user = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("No se ha encontrado el usuario con el ID " + jwtService.extractUserIDFromToken(token));
        }

        manager stabManager = managerRepo.findByUserID(user);
        if (stabManager == null) {
            throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + jwtService.extractUserIDFromToken(token));
        }

        if (!stabManager.isOwner()) {
            throw new ManagerNotOwnerException("El manager con el ID " + jwtService.extractUserIDFromToken(token) + " no puede crear un establecimiento");
        }
        List<manager> managerList = new ArrayList<>();
        managerList.add(stabManager);

        stabManager.getStablishmentID().add(newStab);
        newStab.setManagerID(managerList);
        managerRepo.save(stabManager);
        stabRepo.save(newStab);
    }

    public void addWorkerToStab(Long stablishmentID, worker targetWorker, String token){
        stablishment targetStab = stabRepo.findById(stablishmentID).orElse(null);
        if (targetStab == null) {
            throw new StablishmentNotFoundException("No se ha encontrado el establecimiento con el ID " + stablishmentID);
        }

        manager stabManager = managerRepo.findByUserID(userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null));
        if (stabManager == null) {
            throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + jwtService.extractUserIDFromToken(token));
        }

        user targetUser = userRepo.findById(targetWorker.getUserID().getUserID()).orElse(null);
        if (targetUser == null) {
            throw new UserNotFoundException("No se ha encontrado el usuario con el ID " + targetWorker.getUserID().getUserID());
        }

        if (!isManagerInStab(targetStab, stabManager)){
            throw new ManagerNotFromStablishmentException("El establecimiento con el ID " + stablishmentID + " no pertenece al manager con el ID " + jwtService.extractUserIDFromToken(token));
        }

        targetUser.setUserRole(role.WORKER);
        targetWorker.setStablishmentID(targetStab);
        targetWorker.setWorkingHours(160L);
        targetStab.getWorkers().add(targetWorker);
        stabRepo.save(targetStab);
        workerRepo.save(targetWorker);
        userRepo.save(targetUser);

    }

    public void addWorkerToStabInterestPoint(Long stablishmentID, String userID, Long interestPointID, String token){
        stablishment targetStab = stabRepo.findById(stablishmentID).orElse(null);
        if (targetStab == null) {
            throw new StablishmentNotFoundException("No se ha encontrado el establecimiento con el ID " + stablishmentID);
        }

        user targetUser = userRepo.findById(userID).orElse(null);
        if (targetUser == null) {
            throw new UserNotFoundException("No se ha encontrado el usuario con el ID " + userID);
        }

        manager stabManager = managerRepo.findByUserID(userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null));
        if (stabManager == null) {
            throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + jwtService.extractUserIDFromToken(token));
        }

        worker worker = workerRepo.findByUserIDAndStablishmentID(targetUser, targetStab);
        if (worker == null) {
            throw new WorkerNotFoundException("No se ha encontrado el trabajador con el ID " + userID + " en el establecimiento con el ID " + stablishmentID);
        }

        interestPoint interestPoint = interestPointRepo.findById(interestPointID).orElse(null);
        if (interestPoint == null) {
            throw new InterestPointNotFoundException("No se ha encontrado el punto de interés con el ID " + interestPointID);
        }

        if (!isManagerInStab(targetStab, stabManager)){
            throw new ManagerNotFromStablishmentException("El establecimiento con el ID " + stablishmentID + " no pertenece al manager con el ID " + jwtService.extractUserIDFromToken(token));
        }

        if (interestPoint.getStablishmentID() != targetStab){
            throw new InterestPointNotFromStablishmentException("El punto de interés con el ID " + interestPointID + " no pertenece al establecimiento con el ID " + stablishmentID);
        }

        if (worker.getStablishmentID() != targetStab){
            throw new WorkerNotFromStablishmentException("El trabajador con el ID " + userID + " no pertenece al establecimiento con el ID " + stablishmentID);
        }

        worker.setInterestPointID(interestPoint);
        interestPoint.getWorkers().add(worker);
        interestPointRepo.save(interestPoint);
        workerRepo.save(worker);
    }

    public void addWorkerToEventInterestPoint(Long stablishmentID, String eventName, String userID, Long interestPointID, String token){
        stablishment targetStab = stabRepo.findById(stablishmentID).orElse(null);
        if (targetStab == null) {
            throw new StablishmentNotFoundException("No se ha encontrado el establecimiento con el ID " + stablishmentID);
        }

        user targetUser = userRepo.findById(userID).orElse(null);
        if (targetUser == null) {
            throw new UserNotFoundException("No se ha encontrado el usuario con el ID " + userID);
        }

        manager stabManager = managerRepo.findByUserID(userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null));
        if (stabManager == null) {
            throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + jwtService.extractUserIDFromToken(token));
        }

        worker worker = workerRepo.findByUserIDAndStablishmentID(targetUser, targetStab);
        if (worker == null) {
            throw new WorkerNotFoundException("No se ha encontrado el trabajador con el ID " + userID + " en el establecimiento con el ID " + stablishmentID);
        }

        event event = eventRepo.findByEventNameAndStablishmentID(eventName, targetStab);
        if (event == null) {
            throw new EventNotFoundException("No se ha encontrado el evento con el nombre " + eventName + " en el establecimiento con el ID " + stablishmentID);
        }

        interestPoint interestPoint = interestPointRepo.findById(interestPointID).orElse(null);
        if (interestPoint == null) {
            throw new InterestPointNotFoundException("No se ha encontrado el punto de interés con el ID " + interestPointID);
        }

        if (worker.getStablishmentID() != targetStab){
            throw new WorkerNotFromStablishmentException("El trabajador con el ID " + userID + " no pertenece al establecimiento con el ID " + stablishmentID);
        }

        if (!isManagerInStab(targetStab, stabManager)){
            throw new ManagerNotFromStablishmentException("El establecimiento con el ID " + stablishmentID + " no pertenece al manager con el ID " + jwtService.extractUserIDFromToken(token));
        }

        if (interestPoint.getEventName() != event) {
            throw new InterestPointNotFromEventException("El punto de interés con el ID " + interestPointID + " no pertenece al evento con el nombre " + eventName);
        }

        worker.setInterestPointID(interestPoint);
        interestPoint.getWorkers().add(worker);
        interestPointRepo.save(interestPoint);
        workerRepo.save(worker);
    }

    public void deleteWorkerFromStab(Long stablishmentID, String userID, String token){
        stablishment targetStab = stabRepo.findById(stablishmentID).orElse(null);
        if (targetStab == null) {
            throw new StablishmentNotFoundException("No se ha encontrado el establecimiento con el ID " + stablishmentID);
        }

        user targetUser = userRepo.findById(userID).orElse(null);
        if (targetUser == null) {
            throw new UserNotFoundException("No se ha encontrado el usuario con el ID " + userID);
        }

        manager stabManager = managerRepo.findByUserID(userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null));
        if (stabManager == null) {
            throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + jwtService.extractUserIDFromToken(token));
        }

        worker worker = workerRepo.findByUserIDAndStablishmentID(targetUser, targetStab);
        if (worker == null) {
            throw new WorkerNotFoundException("No se ha encontrado el trabajador con el ID " + userID + " en el establecimiento con el ID " + stablishmentID);
        }

        if (worker.getStablishmentID() != targetStab){
            throw new WorkerNotFromStablishmentException("El trabajador con el ID " + userID + " no pertenece al establecimiento con el ID " + stablishmentID);
        }

        if (!isManagerInStab(targetStab, stabManager)) {
            throw new ManagerNotFromStablishmentException("El establecimiento con el ID " + stablishmentID + " no pertenece al manager con el ID " + jwtService.extractUserIDFromToken(token));
        }
        targetUser.setUserRole(role.USER);
        targetStab.getWorkers().remove(worker);
        userRepo.save(targetUser);
        stabRepo.save(targetStab);
        workerRepo.delete(worker);
    }

    public void updateStab(stablishment targetStab, String token) {
        stablishment stablishment = stabRepo.findById(targetStab.getStablishmentID()).orElse(null);
        if (stablishment == null) {
            throw new StablishmentNotFoundException("No se ha encontrado el establecimiento con el ID " + targetStab.getStablishmentID());
        }

        user user = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("No se ha encontrado el usuario con el ID " + jwtService.extractUserIDFromToken(token));
        }

        manager stabManager = managerRepo.findByUserID(user);
        if (stabManager == null) {
            throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + jwtService.extractUserIDFromToken(token));
        }

        if (!isManagerInStab(targetStab, stabManager)) {
            throw new ManagerNotFromStablishmentException("El establecimiento con el ID " + targetStab.getStablishmentID() + " no pertenece al manager con el ID " + jwtService.extractUserIDFromToken(token));
        }
        stablishment.setCapacity(targetStab.getCapacity());
        stablishment.setStabAddress(targetStab.getStabAddress());
        stablishment.setCloseTime(targetStab.getCloseTime());
        stablishment.setOpenTime(targetStab.getOpenTime());
        stablishment.setStabName(targetStab.getStabName());
        stabRepo.save(stablishment);
    }

    public void addManagerToStab(Long stablishmentID, String userID, String token){
        stablishment targetStab = stabRepo.findById(stablishmentID).orElse(null);
        if (targetStab == null) {
            throw new StablishmentNotFoundException("No se ha encontrado el establecimiento con el ID " + stablishmentID);
        }

        user targetUser = userRepo.findById(userID).orElse(null);
        if (targetUser == null) {
            throw new UserNotFoundException("No se ha encontrado el usuario con el ID " + userID);
        }

        manager stabManager = managerRepo.findByUserID(userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null));
        if (stabManager == null) {
            throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + jwtService.extractUserIDFromToken(token));
        }

        if (!isManagerInStab(targetStab, stabManager)) {
            throw new ManagerNotFromStablishmentException("El establecimiento con el ID " + targetStab.getStablishmentID() + " no pertenece al manager con el ID " + jwtService.extractUserIDFromToken(token));
        }

        manager newManager = new manager();
        newManager.setUserID(targetUser);
        newManager.setOwner(false);
        newManager.getStablishmentID().add(targetStab);

        targetStab.getManagerID().add(newManager);
        stabRepo.save(targetStab);
        managerRepo.save(newManager);
    }

    private boolean isManagerInStab(stablishment targetStab, manager stabManager) {
        for (manager manager : targetStab.getManagerID()) {
            if (manager == stabManager) {
                return true;
            }
        }
        return false;
    }
}
