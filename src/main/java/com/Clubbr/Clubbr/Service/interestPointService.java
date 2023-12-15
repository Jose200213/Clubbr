package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.advice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.Clubbr.Clubbr.Repository.interestPointRepo;
import com.Clubbr.Clubbr.Repository.eventRepo;
import org.springframework.transaction.annotation.Transactional;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Repository.userRepo;
import com.Clubbr.Clubbr.Repository.managerRepo;

import java.util.List;

@Service
public class interestPointService {

    @Autowired
    private interestPointRepo interestPointRepo;

    @Autowired
    private stablishmentService stablishmentService;

    @Autowired
    private eventService eventService;

    @Autowired
    private jwtService jwtService;

    @Autowired
    private userService userService;

    @Autowired
    private managerService managerService;

    @Autowired
    private stablishmentRepo stablishmentRepo;

    @Autowired
    private eventRepo eventRepo;


    @Transactional
    public void addInterestPointToStab(Long stabID, interestPoint newInterestPoint, String token){
        stablishment stablishment = stablishmentService.getStab(stabID);
        user targetUser = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (userService.isManager(targetUser)){
            manager targetManager = managerService.getManager(targetUser);
            if (!managerService.isManagerInStab(stablishment, targetManager)){
                throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + stablishment.getStablishmentID());
            }
        }

        newInterestPoint.setStablishmentID(stablishment);
        stablishment.getInterestPoints().add(newInterestPoint);

        stablishmentRepo.save(stablishment);
        interestPointRepo.save(newInterestPoint);
    }

    @Transactional(readOnly = true)
    public List<interestPoint> getInterestPointsByStablishment(Long stablishmentID){
        stablishment stablishment = stablishmentService.getStab(stablishmentID);
        return interestPointRepo.findByStablishmentID(stablishment);
    }

    public interestPoint getInterestPoint(Long interestPointID){
        return interestPointRepo.findById(interestPointID)
                .orElseThrow(() -> new InterestPointNotFoundException("No se ha encontrado el punto de interés con el ID " + interestPointID));
    }

    @Transactional(readOnly = true)
    public interestPoint getInterestPointByStablishment(Long stablishmentID, Long interestPointID){
        stablishment stablishment = stablishmentService.getStab(stablishmentID);
        interestPoint interestPoint = getInterestPoint(interestPointID);

        if (interestPoint.getStablishmentID() != stablishment){
            throw new InterestPointNotFromStablishmentException("No se ha encontrado el punto de interés con el ID " + interestPointID + " en el establecimiento con el ID " + stablishmentID);
        }
        return interestPoint;
    }

    @Transactional(readOnly = true)
    public interestPoint getInterestPointByEventName(Long stablishmentID, String eventName, Long interestPointID){
        stablishment stablishment = stablishmentService.getStab(stablishmentID);
        event event = eventService.getEventByEventNameAndStablishmentID(eventName, stablishment);
        interestPoint interestPoint = getInterestPoint(interestPointID);

        if (interestPoint.getEventName() != event){
            throw new InterestPointNotFromEventException("No se ha encontrado el punto de interés con el ID " + interestPointID + " en el evento con el nombre " + eventName);
        }
        return interestPoint;
    }

    @Transactional
    public void addInterestPointToEvent(Long stabID, String eventName, interestPoint newInterestPoint, String token){
        stablishment stablishment = stablishmentService.getStab(stabID);
        event event = eventService.getEventByEventNameAndStablishmentID(eventName, stablishment);
        user targetUser = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (userService.isManager(targetUser)){
            manager targetManager = managerService.getManager(targetUser);
            if (!managerService.isManagerInStab(stablishment, targetManager)){
                throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + stablishment.getStablishmentID());
            }
        }

        newInterestPoint.setEventName(event);
        event.getInterestPoints().add(newInterestPoint);

        eventRepo.save(event);
        interestPointRepo.save(newInterestPoint);
    }

    @Transactional(readOnly = true)
    public List<interestPoint> getInterestPointsByEventName(String eventName, Long stablishmentID){
        stablishment stablishment = stablishmentService.getStab(stablishmentID);
        event event = eventService.getEventByEventNameAndStablishmentID(eventName, stablishment);
        return interestPointRepo.findByEventName(event);
    }

    @Transactional
    public void updateInterestPointFromStablishment(Long stablishmentID, Long interestPointID, interestPoint targetInterestPoint, String token){
        interestPoint interestPoint = getInterestPointByStablishment(stablishmentID, interestPointID);
        user targetUser = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (userService.isManager(targetUser)){
            manager targetManager = managerService.getManager(targetUser);
            if (!managerService.isManagerInStab(interestPoint.getStablishmentID(), targetManager)){
                throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + interestPoint.getStablishmentID().getStablishmentID());
            }
        }

        interestPoint.setDescription(targetInterestPoint.getDescription());
        interestPoint.setWorkers(targetInterestPoint.getWorkers());
        interestPoint.setXCoordinate(targetInterestPoint.getXCoordinate());
        interestPoint.setYCoordinate(targetInterestPoint.getYCoordinate());
        interestPointRepo.save(interestPoint);
    }

    @Transactional
    public void updateInterestPointFromEvent(Long stablishmentID, String eventName, Long interestPointID, interestPoint targetInterestPoint, String token){
        interestPoint interestPoint = getInterestPointByEventName(stablishmentID, eventName, interestPointID);
        stablishment stablishment = stablishmentService.getStab(stablishmentID);
        user targetUser = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (userService.isManager(targetUser)){
            manager targetManager = managerService.getManager(targetUser);
            if (!managerService.isManagerInStab(stablishment, targetManager)){
                throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + stablishment.getStablishmentID());
            }
        }

        interestPoint.setDescription(targetInterestPoint.getDescription());
        interestPoint.setWorkers(targetInterestPoint.getWorkers());
        interestPoint.setXCoordinate(targetInterestPoint.getXCoordinate());
        interestPoint.setYCoordinate(targetInterestPoint.getYCoordinate());
        interestPointRepo.save(interestPoint);
    }

    @Transactional
    public void deleteInterestPointFromStablishment(Long stablishmentID, Long interestPointID, String token){
        interestPoint interestPoint = getInterestPointByStablishment(stablishmentID, interestPointID);
        user targetUser = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (userService.isManager(targetUser)){
            manager targetManager = managerService.getManager(targetUser);
            if (!managerService.isManagerInStab(interestPoint.getStablishmentID(), targetManager)){
                throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + interestPoint.getStablishmentID().getStablishmentID());
            }
        }

        interestPointRepo.delete(interestPoint);
    }

    @Transactional
    public void deleteInterestPointFromEvent(Long stablishmentID, String eventName, Long interestPointID, String token) {
        stablishment stablishment = stablishmentService.getStab(stablishmentID);
        interestPoint interestPoint = getInterestPointByEventName(stablishmentID, eventName, interestPointID);
        user targetUser = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (userService.isManager(targetUser)) {
            manager targetManager = managerService.getManager(targetUser);
            if (!managerService.isManagerInStab(stablishment, targetManager)) {
                throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + stablishment.getStablishmentID());
            }
        }

        interestPointRepo.delete(interestPoint);
    }
}
