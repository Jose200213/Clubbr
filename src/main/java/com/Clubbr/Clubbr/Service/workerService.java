package com.Clubbr.Clubbr.Service;

import java.util.List;

import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.advice.*;
import com.Clubbr.Clubbr.utils.role;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Repository.workerRepo;
<<<<<<< HEAD
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.user;
=======
import com.Clubbr.Clubbr.Repository.interestPointRepo;
>>>>>>> PruebaMerge06-12-2023

@Service
public class workerService {

    @Autowired
    private workerRepo workerRepo;

<<<<<<< HEAD
        public List<worker> getAllWorkers(stablishment stablishment) {
                return workerRepo.findAllByStablishmentID(stablishment);

        }
        public worker getWorker(user user, stablishment stablishment) {
                return workerRepo.findByUserIDAndStablishmentID(user, stablishment);
        }
        public void deleteWorker(user user, stablishment stablishment) {
                workerRepo.deleteByUserIDAndStablishmentID(user, stablishment);
        }
        public void addWorker(worker newWorker) {workerRepo.save(newWorker);
        }
        public void updateWorker(worker targetWorker) {workerRepo.save(targetWorker);
        }

=======
    @Autowired
    private userService userService;

    @Autowired
    private managerService managerService;

    @Autowired
    private jwtService jwtService;

    @Autowired
    private stablishmentService stablishmentService;

    @Autowired
    private interestPointService interestPointService;

    @Autowired
    private interestPointRepo interestPointRepo;

    @Autowired
    private eventService eventService;

    public List<worker> getAllWorkers(Long stablishmentID, String token) {
        stablishment targetStablishment = stablishmentService.getStab(stablishmentID);
        user targetUser = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (userService.isManager(targetUser)){
            manager targetManager = managerService.getManager(targetUser);
            if (!managerService.isManagerInStab(targetStablishment, targetManager)){
                throw new ResourceNotFoundException("Manager", "userID", targetUser.getUserID(), "Establecimiento", "stablishmentID", targetStablishment.getStablishmentID());
            }
        }

        List<worker> workers = workerRepo.findAllByStablishmentID(targetStablishment);
        if (workers.isEmpty()) {
            throw new ResourceNotFoundException("Trabajadores");
        }
        return workers;
    }


    public worker getWorker(user userID, stablishment stablishmentID) {
        return workerRepo.findByUserIDAndStablishmentID(userID, stablishmentID)
                .orElseThrow(() -> new ResourceNotFoundException("Trabajador", "userID", userID.getUserID(), "Establecimiento", "stablishmentID", stablishmentID.getStablishmentID()));

    }

    public worker getWorkerFromStab(String userID, Long stablishmentID, String token) {
        stablishment targetStablishment = stablishmentService.getStab(stablishmentID);
        user targetUser = userService.getUser(userID);
        user requestUser = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (requestUser.getUserRole() == role.WORKER){
            if (!targetUser.equals(requestUser)){
                throw new ResourceNotFoundException("Trabajador", "userID", targetUser.getUserID());
            }
        }

        if (userService.isManager(requestUser)){
            manager targetManager = managerService.getManager(userService.getUser(jwtService.extractUserIDFromToken(token)));
            if (!managerService.isManagerInStab(targetStablishment, targetManager)){
                throw new ResourceNotFoundException("Manager", "userID", targetUser.getUserID(), "Establecimiento", "stablishmentID", targetStablishment.getStablishmentID());
            }
        }

        return getWorker(targetUser, targetStablishment);
    }

    public void addWorkerToStab(Long stablishmentID, worker targetWorker, String token){
        String userId = jwtService.extractUserIDFromToken(token);
        stablishment targetStab = stablishmentService.getStab(stablishmentID);
        user requestUser = userService.getUser(userId);
        user targetUser = userService.getUser(targetWorker.getUserID().getUserID());

        if (userService.isManager(requestUser)){
            manager targetManager = managerService.getManager(userService.getUser(jwtService.extractUserIDFromToken(token)));
            if (!managerService.isManagerInStab(targetStab, targetManager)){
                throw new ResourceNotFoundException("Manager", "userID", targetUser.getUserID(), "Establecimiento", "stablishmentID", targetStab.getStablishmentID());
            }
        }

        targetUser.setUserRole(role.WORKER);
        targetWorker.setUserID(targetUser);
        targetStab.getWorkers().add(targetWorker);
        targetWorker.setStablishmentID(targetStab);
        targetWorker.setWorkingHours(160L);
        workerRepo.save(targetWorker);
    }

    public void addWorkerToStabInterestPoint(Long stablishmentID, String userID, Long interestPointID, String token){
        String userId = jwtService.extractUserIDFromToken(token);
        user requestUser = userService.getUser(userId);
        stablishment targetStab = stablishmentService.getStab(stablishmentID);
        worker worker = getWorker(userService.getUser(userID), targetStab);
        interestPoint interestPoint = interestPointService.getInterestPointByStablishment(targetStab.getStablishmentID(), interestPointID);

        if (userService.isManager(requestUser)){
            manager targetManager = managerService.getManager(requestUser);
            if (!managerService.isManagerInStab(targetStab, targetManager)){
                throw new ResourceNotFoundException("Manager", "userID", requestUser.getUserID(), "Establecimiento", "stablishmentID", targetStab.getStablishmentID());
            }
        }

        worker.setInterestPointID(interestPoint);
        interestPoint.getWorkers().add(worker);
        interestPointRepo.save(interestPoint);
        workerRepo.save(worker);
    }

    public void addWorkerToEventInterestPoint(Long stablishmentID, String eventName, String userID, Long interestPointID, String token){
        String userId = jwtService.extractUserIDFromToken(token);
        stablishment targetStab = stablishmentService.getStab(stablishmentID);
        user requestUser = userService.getUser(userId);
        worker worker = getWorker(userService.getUser(userID), targetStab);
        event event = eventService.getEventByEventNameAndStablishmentID(eventName, targetStab);
        interestPoint interestPoint = interestPointService.getInterestPointByEventName(stablishmentID, event.getEventName(), interestPointID);

        if (userService.isManager(requestUser)){
            manager targetManager = managerService.getManager(requestUser);
            if (!managerService.isManagerInStab(targetStab, targetManager)){
                throw new ResourceNotFoundException("Manager", "userID", requestUser.getUserID(), "Establecimiento", "stablishmentID", targetStab.getStablishmentID());
            }
        }

        worker.setInterestPointID(interestPoint);
        interestPoint.getWorkers().add(worker);
        interestPointRepo.save(interestPoint);
        workerRepo.save(worker);
    }

    public void deleteWorkerFromStab(Long stablishmentID, String userID, String token){
        String userId = jwtService.extractUserIDFromToken(token);
        stablishment targetStab = stablishmentService.getStab(stablishmentID);
        user requestUser = userService.getUser(userId);
        worker worker = getWorker(userService.getUser(userID), targetStab);

        if (userService.isManager(requestUser)){
            manager targetManager = managerService.getManager(requestUser);
            if (!managerService.isManagerInStab(targetStab, targetManager)){
                throw new ResourceNotFoundException("Manager", "userID", requestUser.getUserID(), "Establecimiento", "stablishmentID", targetStab.getStablishmentID());
            }
        }

        worker.getUserID().setUserRole(role.USER);
        workerRepo.delete(worker);
    }

    public void updateWorker(Long stablishmentID, worker targetWorker, String token) {
        user requestUser = userService.getUser(targetWorker.getUserID().getUserID());
        stablishment stablishment = stablishmentService.getStab(stablishmentID);

        if (userService.isManager(requestUser)){
            manager targetManager = managerService.getManager(requestUser);
            if (!managerService.isManagerInStab(stablishment, targetManager)){
                throw new ResourceNotFoundException("Manager", "userID", requestUser.getUserID(), "Establecimiento", "stablishmentID", stablishment.getStablishmentID());
            }
        }

        worker workerToUpdate = getWorker(targetWorker.getUserID(), targetWorker.getStablishmentID());
        workerToUpdate.setInterestPointID(targetWorker.getInterestPointID());
        workerToUpdate.setSalary(targetWorker.getSalary());
        workerToUpdate.setWorkingHours(targetWorker.getWorkingHours());
        workerRepo.save(workerToUpdate);
    }
>>>>>>> PruebaMerge06-12-2023
}
