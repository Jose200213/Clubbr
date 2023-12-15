package com.Clubbr.Clubbr.Service;

import java.util.List;

import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.advice.*;
import com.Clubbr.Clubbr.utils.role;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Repository.workerRepo;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Repository.userRepo;
import com.Clubbr.Clubbr.Repository.managerRepo;
import com.Clubbr.Clubbr.Repository.interestPointRepo;

@Service
public class workerService {

    @Autowired
    private workerRepo workerRepo;

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
                throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + targetStablishment.getStablishmentID());
            }
        }

    return workerRepo.findAllByStablishmentID(targetStablishment);
    }


    public worker getWorker(user userID, stablishment stablishmentID) {
        return workerRepo.findByUserIDAndStablishmentID(userID, stablishmentID)
                .orElseThrow(() -> new WorkerNotFoundException("No se ha encontrado el trabajador con el ID " + userID.getUserID()));

    }

    public worker getWorkerFromStab(String userID, Long stablishmentID, String token) {
        stablishment targetStablishment = stablishmentService.getStab(stablishmentID);
        user targetUser = userService.getUser(userID);
        user requestUser = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (requestUser.getUserRole() == role.WORKER){
            if (!targetUser.equals(requestUser)){
                throw new WorkerNotFoundException("No se ha encontrado el trabajador con el ID " + targetUser.getUserID());
            }
        }

        if (userService.isManager(requestUser)){
            manager targetManager = managerService.getManager(userService.getUser(jwtService.extractUserIDFromToken(token)));
            if (!managerService.isManagerInStab(targetStablishment, targetManager)){
                throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + targetStablishment.getStablishmentID());
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
                throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + targetStab.getStablishmentID());
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
            manager targetManager = managerService.getManager(userService.getUser(jwtService.extractUserIDFromToken(token)));
            if (!managerService.isManagerInStab(targetStab, targetManager)){
                throw new ManagerNotFromStablishmentException("El manager con el ID " + requestUser.getUserID() + " no es manager del establecimiento con el ID " + targetStab.getStablishmentID());
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
            manager targetManager = managerService.getManager(userService.getUser(jwtService.extractUserIDFromToken(token)));
            if (!managerService.isManagerInStab(targetStab, targetManager)){
                throw new ManagerNotFromStablishmentException("El manager con el ID " + requestUser.getUserID() + " no es manager del establecimiento con el ID " + targetStab.getStablishmentID());
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
            manager targetManager = managerService.getManager(userService.getUser(jwtService.extractUserIDFromToken(token)));
            if (!managerService.isManagerInStab(targetStab, targetManager)){
                throw new ManagerNotFromStablishmentException("El manager con el ID " + requestUser.getUserID() + " no es manager del establecimiento con el ID " + targetStab.getStablishmentID());
            }
        }

        worker.getUserID().setUserRole(role.USER);
        workerRepo.delete(worker);
    }

    public void updateWorker(Long stablishmentID, worker targetWorker, String token) {
        user requestUser = userService.getUser(targetWorker.getUserID().getUserID());
        stablishment stablishment = stablishmentService.getStab(stablishmentID);

        if (userService.isManager(requestUser)){
            manager targetManager = managerService.getManager(userService.getUser(jwtService.extractUserIDFromToken(token)));
            if (!managerService.isManagerInStab(stablishment, targetManager)){
                throw new ManagerNotFromStablishmentException("El manager con el ID " + requestUser.getUserID() + " no es manager del establecimiento con el ID " + stablishment.getStablishmentID());
            }
        }

        worker workerToUpdate = getWorker(targetWorker.getUserID(), targetWorker.getStablishmentID());
        workerToUpdate.setInterestPointID(targetWorker.getInterestPointID());
        workerToUpdate.setSalary(targetWorker.getSalary());
        workerToUpdate.setWorkingHours(targetWorker.getWorkingHours());
        workerRepo.save(workerToUpdate);
    }
}
