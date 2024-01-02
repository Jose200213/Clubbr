package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Dto.interestPointDto;
import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.advice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Clubbr.Clubbr.Repository.interestPointRepo;
import com.Clubbr.Clubbr.Repository.eventRepo;
import org.springframework.transaction.annotation.Transactional;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;

import java.time.LocalDate;
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
                throw new ResourceNotFoundException("Manager", "userID", targetUser.getUserID(), "Establecimiento", "stablishmentID", stablishment.getStablishmentID());
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
                .orElseThrow(() -> new ResourceNotFoundException("Punto de interés", "interestPointID", interestPointID));
    }

    public interestPoint getInterestPointByStablishment(Long stablishmentID, Long interestPointID){
        stablishment stablishment = stablishmentService.getStab(stablishmentID);
        interestPoint interestPoint = getInterestPoint(interestPointID);

        if (interestPoint.getStablishmentID() != stablishment){
            throw new ResourceNotFoundException("Punto de interés", "interestPointID", interestPointID, "Establecimiento", "stablishmentID", stablishment.getStablishmentID());
        }
        return interestPoint;
    }

    public interestPointDto getInterestPointDto(interestPoint interestPoint){
        return new interestPointDto(interestPoint);
    }

    public List<interestPointDto> getInterestPointListDto(List<interestPoint> interestPoint){
        return interestPoint.stream().map(interestPointDto::new).collect(java.util.stream.Collectors.toList());
    }

    @Transactional(readOnly = true)
    public interestPoint getInterestPointByEventName(Long stablishmentID, String eventName, LocalDate eventDate, Long interestPointID){
        stablishment stablishment = stablishmentService.getStab(stablishmentID);
        event event = eventService.getEventByStabNameDate(stablishment.getStablishmentID(), eventName, eventDate);
        interestPoint interestPoint = getInterestPoint(interestPointID);

        if (interestPoint.getEventName() != event){
            throw new ResourceNotFoundException("Punto de interés", "interestPointID", interestPointID, "Evento", "eventName", event.getEventName());
        }
        return interestPoint;
    }

    @Transactional
    public void addInterestPointToEvent(Long stabID, String eventName, LocalDate eventDate, interestPoint newInterestPoint, String token){
        stablishment stablishment = stablishmentService.getStab(stabID);
        event event = eventService.getEventByStabNameDate(stablishment.getStablishmentID(), eventName, eventDate);
        user targetUser = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (userService.isManager(targetUser)){
            manager targetManager = managerService.getManager(targetUser);
            if (!managerService.isManagerInStab(stablishment, targetManager)){
                throw new ResourceNotFoundException("Manager", "userID", targetUser.getUserID(), "Establecimiento", "stablishmentID", stablishment.getStablishmentID());
            }
        }

        newInterestPoint.setEventName(event);
        event.getInterestPoints().add(newInterestPoint);

        eventRepo.save(event);
        interestPointRepo.save(newInterestPoint);
    }

    @Transactional(readOnly = true)
    public List<interestPoint> getInterestPointsByEventName(String eventName, LocalDate eventDate, Long stablishmentID){
        stablishment stablishment = stablishmentService.getStab(stablishmentID);
        event event = eventService.getEventByStabNameDate(stablishment.getStablishmentID(), eventName, eventDate);
        return interestPointRepo.findByEventName(event);
    }

    @Transactional
    public void updateInterestPointFromStablishment(Long stablishmentID, Long interestPointID, interestPoint targetInterestPoint, String token){
        interestPoint interestPoint = getInterestPointByStablishment(stablishmentID, interestPointID);
        user targetUser = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (userService.isManager(targetUser)){
            manager targetManager = managerService.getManager(targetUser);
            if (!managerService.isManagerInStab(interestPoint.getStablishmentID(), targetManager)){
                throw new ResourceNotFoundException("Manager", "userID", targetUser.getUserID(), "Establecimiento", "stablishmentID", interestPoint.getStablishmentID().getStablishmentID());
            }
        }

        interestPoint.setDescription(targetInterestPoint.getDescription());
        interestPoint.setWorkers(targetInterestPoint.getWorkers());
        interestPoint.setXCoordinate(targetInterestPoint.getXCoordinate());
        interestPoint.setYCoordinate(targetInterestPoint.getYCoordinate());
        interestPointRepo.save(interestPoint);
    }

    @Transactional
    public void updateInterestPointFromEvent(Long stablishmentID, String eventName, LocalDate eventDate, Long interestPointID, interestPoint targetInterestPoint, String token){
        interestPoint interestPoint = getInterestPointByEventName(stablishmentID, eventName, eventDate, interestPointID);
        stablishment stablishment = stablishmentService.getStab(stablishmentID);
        user targetUser = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (userService.isManager(targetUser)){
            manager targetManager = managerService.getManager(targetUser);
            if (!managerService.isManagerInStab(stablishment, targetManager)){
                throw new ResourceNotFoundException("Manager", "userID", targetUser.getUserID(), "Establecimiento", "stablishmentID", stablishment.getStablishmentID());
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
                throw new ResourceNotFoundException("Manager", "userID", targetUser.getUserID(), "Establecimiento", "stablishmentID", interestPoint.getStablishmentID().getStablishmentID());
            }
        }

        interestPointRepo.delete(interestPoint);
    }

    @Transactional
    public void deleteInterestPointFromEvent(Long stablishmentID, String eventName, LocalDate eventDate, Long interestPointID, String token) {
        stablishment stablishment = stablishmentService.getStab(stablishmentID);
        interestPoint interestPoint = getInterestPointByEventName(stablishmentID, eventName, eventDate, interestPointID);
        user targetUser = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (userService.isManager(targetUser)) {
            manager targetManager = managerService.getManager(targetUser);
            if (!managerService.isManagerInStab(stablishment, targetManager)) {
                throw new ResourceNotFoundException("Manager", "userID", targetUser.getUserID(), "Establecimiento", "stablishmentID", stablishment.getStablishmentID());
            }
        }

        interestPointRepo.delete(interestPoint);
    }
}