package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.stablishment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Clubbr.Clubbr.Repository.eventRepo;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class eventService {

    @Autowired
    private eventRepo eventRepo;

    @Autowired
    private stablishmentRepo stablishmentRepo;

    //public List<event> getAllEvents(stablishment stablishmentID) {
     //   return eventRepo.findAllByStablishmentID(stablishmentID.getStablishmentID());
   // }

    @Transactional
    public void addEventToStab(Long stabID, event newEvent){
        stablishment stablishment = stablishmentRepo.findById(stabID).orElse(null);

        newEvent.setStablishmentID(stablishment);
        stablishment.getEvents().add(newEvent);

        eventRepo.save(newEvent);
        stablishmentRepo.save(stablishment);
    }
}
