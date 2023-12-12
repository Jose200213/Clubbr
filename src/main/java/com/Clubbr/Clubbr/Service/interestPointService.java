package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.advice.ManagerNotFoundException;
import com.Clubbr.Clubbr.advice.ManagerNotFromStablishmentException;
import org.springframework.beans.factory.annotation.Autowired;
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
    private stablishmentRepo stablishmentRepo;

    @Autowired
    private eventRepo eventRepo;

    @Autowired
    private jwtService jwtService;

    @Autowired
    private userRepo userRepo;

    @Autowired
    private managerRepo managerRepo;

    @Autowired
    private stablishmentService stablishmentService;

    @Transactional
    public void addInterestPointToStab(Long stabID, interestPoint newInterestPoint, String token){
        stablishment stablishment = stablishmentRepo.findById(stabID).orElse(null);
        user targetUser = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
        manager targetManager = managerRepo.findByUserID(targetUser).orElse(null);
        if (targetManager == null){
            throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + targetUser.getUserID());
        }

        if (!stablishmentService.isManagerInStab(stablishment, targetManager)){
            throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + stablishment.getStablishmentID());
        }

        newInterestPoint.setStablishmentID(stablishment);
        stablishment.getInterestPoints().add(newInterestPoint);

        interestPointRepo.save(newInterestPoint);
        stablishmentRepo.save(stablishment);
    }

    @Transactional(readOnly = true)
    public List<interestPoint> getInterestPointsByStablishment(Long stablishmentID){
        stablishment stablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
        return interestPointRepo.findByStablishmentID(stablishment);
    }

    @Transactional(readOnly = true)
    public interestPoint getInterestPointByStablishment(Long stablishmentID, Long interestPointID){
        stablishment stablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
        interestPoint interestPoint = interestPointRepo.findById(interestPointID).orElse(null);

        if (interestPoint.getStablishmentID() == stablishment){
            return interestPoint;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public interestPoint getInterestPointByEventName(Long stablishmentID, String eventName, Long interestPointID){
        stablishment stablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
        event event = eventRepo.findByEventNameAndStablishmentID(eventName, stablishment);
        interestPoint interestPoint = interestPointRepo.findById(interestPointID).orElse(null);

        if (interestPoint.getEventName() == event){
            return interestPoint;
        }
        return null;
    }

    @Transactional
    public void addInterestPointToEvent(Long stabID, String eventName, interestPoint newInterestPoint, String token){
        stablishment stablishment = stablishmentRepo.findById(stabID).orElse(null);
        event event = eventRepo.findByEventNameAndStablishmentID(eventName, stablishment);
        user targetUser = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
        manager targetManager = managerRepo.findByUserID(targetUser).orElse(null);
        if (targetManager == null){
            throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + targetUser.getUserID());
        }

        if (!stablishmentService.isManagerInStab(stablishment, targetManager)){
            throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + stablishment.getStablishmentID());
        }

        newInterestPoint.setEventName(event);
        event.getInterestPoints().add(newInterestPoint);

        interestPointRepo.save(newInterestPoint);
        eventRepo.save(event);
    }

    @Transactional(readOnly = true)
    public List<interestPoint> getInterestPointsByEventName(String eventName, Long stablishmentID){
        stablishment stablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
        event event = eventRepo.findByEventNameAndStablishmentID(eventName, stablishment);
        return interestPointRepo.findByEventName(event);
    }

    @Transactional
    public void updateInterestPointFromStablishment(Long stablishmentID, Long interestPointID, interestPoint targetInterestPoint, String token){
        stablishment stablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
        interestPoint interestPoint = interestPointRepo.findById(interestPointID).orElse(null);
        user targetUser = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
        manager targetManager = managerRepo.findByUserID(targetUser).orElse(null);
        if (targetManager == null){
            throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + targetUser.getUserID());
        }

        if (!stablishmentService.isManagerInStab(stablishment, targetManager)){
            throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + stablishment.getStablishmentID());
        }

        if (interestPoint.getStablishmentID() == stablishment){
            interestPoint.setDescription(targetInterestPoint.getDescription());
            interestPoint.setWorkers(targetInterestPoint.getWorkers());
            interestPoint.setXCoordinate(targetInterestPoint.getXCoordinate());
            interestPoint.setYCoordinate(targetInterestPoint.getYCoordinate());
            interestPointRepo.save(interestPoint);
        }
    }

    @Transactional
    public void updateInterestPointFromEvent(Long stablishmentID, String eventName, Long interestPointID, interestPoint targetInterestPoint, String token){
        stablishment stablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
        event event = eventRepo.findByEventNameAndStablishmentID(eventName, stablishment);
        interestPoint interestPoint = interestPointRepo.findById(interestPointID).orElse(null);
        user targetUser = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
        manager targetManager = managerRepo.findByUserID(targetUser).orElse(null);
        if (targetManager == null){
            throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + targetUser.getUserID());
        }

        if (!stablishmentService.isManagerInStab(stablishment, targetManager)){
            throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + stablishment.getStablishmentID());
        }

        if (interestPoint.getEventName() == event){
            interestPoint.setDescription(targetInterestPoint.getDescription());
            interestPoint.setWorkers(targetInterestPoint.getWorkers());
            interestPoint.setXCoordinate(targetInterestPoint.getXCoordinate());
            interestPoint.setYCoordinate(targetInterestPoint.getYCoordinate());
            interestPointRepo.save(interestPoint);
        }
    }

    @Transactional
    public void deleteInterestPointFromStablishment(Long stablishmentID, Long interestPointID, String token){
        stablishment stablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
        interestPoint interestPoint = interestPointRepo.findById(interestPointID).orElse(null);
        user targetUser = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
        manager targetManager = managerRepo.findByUserID(targetUser).orElse(null);
        if (targetManager == null){
            throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + targetUser.getUserID());
        }

        if (!stablishmentService.isManagerInStab(stablishment, targetManager)){
            throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + stablishment.getStablishmentID());
        }

        if (interestPoint.getStablishmentID() == stablishment){
            interestPointRepo.deleteById(interestPointID);
        }
    }

    @Transactional
    public void deleteInterestPointFromEvent(Long stablishmentID, String eventName, Long interestPointID, String token){
        stablishment stablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
        event event = eventRepo.findByEventNameAndStablishmentID(eventName, stablishment);
        interestPoint interestPoint = interestPointRepo.findById(interestPointID).orElse(null);
        user targetUser = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
        manager targetManager = managerRepo.findByUserID(targetUser).orElse(null);
        if (targetManager == null){
            throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + targetUser.getUserID());
        }

        if (!stablishmentService.isManagerInStab(stablishment, targetManager)){
            throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + stablishment.getStablishmentID());
        }

        if (interestPoint.getEventName() == event){
            interestPointRepo.deleteById(interestPointID);
        }
    }
}