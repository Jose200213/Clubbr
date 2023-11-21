package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.interestPoint;
import com.Clubbr.Clubbr.Entity.stablishment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Clubbr.Clubbr.Repository.interestPointRepo;
import com.Clubbr.Clubbr.Repository.eventRepo;
import org.springframework.transaction.annotation.Transactional;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class interestPointService {

    @Autowired
    private interestPointRepo interestPointRepo;

    @Autowired
    private stablishmentRepo stablishmentRepo;

    @Autowired
    private eventRepo eventRepo;

    @Transactional
    public void addInterestPointToStab(Long stabID, interestPoint newInterestPoint){
        stablishment stablishment = stablishmentRepo.findById(stabID).orElse(null);

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

    @Transactional
    public void addInterestPointToEvent(Long stabID, String eventName, interestPoint newInterestPoint){
        stablishment stablishment = stablishmentRepo.findById(stabID).orElse(null);
        event event = eventRepo.findByEventNameAndStablishmentID(eventName, stablishment);

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
    public void updateInterestPointFromStablishment(Long stablishmentID, interestPoint targetInterestPoint){
        stablishment stablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
        if (targetInterestPoint.getStablishmentID() == stablishment){
            interestPointRepo.save(targetInterestPoint);
        }
    }

    @Transactional
    public void updateInterestPointFromEvent(Long stablishmentID, String eventName, interestPoint targetInterestPoint){
        stablishment stablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
        event event = eventRepo.findByEventNameAndStablishmentID(eventName, stablishment);
        if (targetInterestPoint.getEventName() == event){
            interestPointRepo.save(targetInterestPoint);
        }
    }

    @Transactional
    public void deleteInterestPointFromStablishment(Long stablishmentID, Long interestPointID){
        stablishment stablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
        interestPoint interestPoint = interestPointRepo.findById(interestPointID).orElse(null);
        if (interestPoint.getStablishmentID() == stablishment){
            interestPointRepo.deleteById(interestPointID);
        }
    }

    @Transactional
    public void deleteInterestPointFromEvent(Long stablishmentID, String eventName, Long interestPointID){
        stablishment stablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
        event event = eventRepo.findByEventNameAndStablishmentID(eventName, stablishment);
        interestPoint interestPoint = interestPointRepo.findById(interestPointID).orElse(null);
        if (interestPoint.getEventName() == event){
            interestPointRepo.deleteById(interestPointID);
        }
    }
}
