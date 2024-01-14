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



    /**
     * This method is used to add a new interest point to a specific establishment.
     *
     * @param stabID This is the ID of the establishment where the new interest point will be added.
     * @param newInterestPoint This is the new interest point that will be added to the establishment.
     * @param token This is the JWT token of the user who is trying to add the new interest point.
     *
     * The method first retrieves the establishment and the user from their respective services using the provided IDs.
     * It then checks if the user is a manager. If the user is a manager, it retrieves the manager details and checks if the manager is associated with the establishment.
     * If the manager is not associated with the establishment, it throws a ResourceNotFoundException.
     * If the user is not a manager or if the manager is associated with the establishment, it sets the establishment ID for the new interest point and adds the new interest point to the establishment's list of interest points.
     * Finally, it saves the updated establishment and the new interest point to their respective repositories.
     */
    @Transactional
    public void addInterestPointToStab(Long stabID, interestPoint newInterestPoint, String token){
        managerService.checkManagerIsFromStab(stabID, token);
        stablishment stablishment = stablishmentService.getStab(stabID);


        newInterestPoint.setStablishmentID(stablishment);
        stablishment.getInterestPoints().add(newInterestPoint);

        stablishmentRepo.save(stablishment);
        interestPointRepo.save(newInterestPoint);
    }


    /**
     * This method is used to retrieve all interest points associated with a specific establishment.
     *
     * @param stablishmentID This is the ID of the establishment whose interest points are to be retrieved.
     * @return List<interestPoint> This returns a list of interest points associated with the specified establishment.
     *
     * The method first retrieves the establishment using the provided ID.
     * It then retrieves all interest points associated with this establishment from the interest point repository.
     */
    @Transactional(readOnly = true)
    public List<interestPoint> getInterestPointsByStablishment(Long stablishmentID){
        return interestPointRepo.findAllByStablishmentIDAndEventNameIsNull(stablishmentService.getStab(stablishmentID));
    }


    /**
     * This method is used to retrieve a specific interest point.
     *
     * @param interestPointID This is the ID of the interest point to be retrieved.
     * @return interestPoint This returns the interest point with the specified ID.
     * @throws ResourceNotFoundException This exception is thrown when the interest point with the specified ID does not exist.
     *
     * The method retrieves the interest point from the interest point repository using the provided ID.
     * If the interest point does not exist, it throws a ResourceNotFoundException.
     */
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

    /**
     * This method is used to retrieve all interest points associated with a specific event.
     * @param interestPoint This is the interest point to be converted to a DTO.
     * @return interestPointDto This returns the DTO of the specified interest point.
     * @throws ResourceNotFoundException This exception is thrown when the interest point with the specified ID does not exist.
     * The method retrieves the interest point from the interest point repository using the provided ID.
     * If the interest point does not exist, it throws a ResourceNotFoundException.
     * Otherwise, it returns the DTO of the interest point.
     */
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
        managerService.checkManagerIsFromStab(stabID, token);
        event event = eventService.getEventByStabNameDate(stablishmentService.getStab(stabID).getStablishmentID(), eventName, eventDate);


        newInterestPoint.setEventName(event);
        event.getInterestPoints().add(newInterestPoint);

        eventRepo.save(event);
        interestPointRepo.save(newInterestPoint);
    }

    @Transactional(readOnly = true)
    public List<interestPoint> getInterestPointsByEventName(String eventName, LocalDate eventDate, Long stablishmentID){
        stablishment stablishment = stablishmentService.getStab(stablishmentID);
        event event = eventService.getEventByStabNameDate(stablishmentService.getStab(stablishmentID).getStablishmentID(), eventName, eventDate);
        return interestPointRepo.findAllByStablishmentIDAndEventNameIsNullOrStablishmentIDIsNullAndEventName(stablishment, event);
    }

    @Transactional
    public void updateInterestPointFromStablishment(Long stablishmentID, Long interestPointID, interestPoint targetInterestPoint, String token){
        managerService.checkManagerIsFromStab(stablishmentID, token);
        interestPoint interestPoint = getInterestPointByStablishment(stablishmentID, interestPointID);


        interestPoint.setDescription(targetInterestPoint.getDescription());
        interestPoint.setWorkers(targetInterestPoint.getWorkers());
        interestPoint.setXCoordinate(targetInterestPoint.getXCoordinate());
        interestPoint.setYCoordinate(targetInterestPoint.getYCoordinate());
        interestPointRepo.save(interestPoint);
    }

    @Transactional
    public void updateInterestPointFromEvent(Long stablishmentID, String eventName, LocalDate eventDate, Long interestPointID, interestPoint targetInterestPoint, String token){
        managerService.checkManagerIsFromStab(stablishmentID, token);
        interestPoint interestPoint = getInterestPointByEventName(stablishmentID, eventName, eventDate, interestPointID);


        interestPoint.setDescription(targetInterestPoint.getDescription());
        interestPoint.setWorkers(targetInterestPoint.getWorkers());
        interestPoint.setXCoordinate(targetInterestPoint.getXCoordinate());
        interestPoint.setYCoordinate(targetInterestPoint.getYCoordinate());
        interestPointRepo.save(interestPoint);
    }

    @Transactional
    public void deleteInterestPointFromStablishment(Long stablishmentID, Long interestPointID, String token){
        managerService.checkManagerIsFromStab(stablishmentID, token);
        interestPoint interestPoint = getInterestPointByStablishment(stablishmentID, interestPointID);


        interestPointRepo.delete(interestPoint);
    }

    @Transactional
    public void deleteInterestPointFromEvent(Long stablishmentID, String eventName, LocalDate eventDate, Long interestPointID, String token) {
        managerService.checkManagerIsFromStab(stablishmentID, token);
        interestPoint interestPoint = getInterestPointByEventName(stablishmentID, eventName, eventDate, interestPointID);

        interestPointRepo.delete(interestPoint);
    }
}