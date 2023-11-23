package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.interestPoint;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Repository.eventRepo;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.config.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class eventService {

    @Autowired
    private eventRepo eventRepo;

    @Autowired
    private stablishmentRepo stabRepo;
    
    //Esta version añade un evento a un local y añade interest points especificos al evento si y solo si los hay en el body.
    //La funcion debe tener manejada si o si la excepcion de eventos con el mismo nombre y fecha pues de no ser asi,
    //jpa no detecta que haya error y al insertar el evento con el mismo nombre y fecha que otro, lo trata como un update
    //y no como un insert, por lo que no se lanza excepcion (restriccion clave primaria) y se actualiza el evento existente y
    //se añaden los interest points (si los hay en el body) al mismo (Todo esto sin añadir un nuevo evento a la DB, si no solo actualizarlo => interest points replicados en DB)
    //////////////////////////////////////////// FUNCION AÑADE EVENTOS E INTEREST_POINTS (OPCIONAL) ////////////////////////////////////////////
    @Transactional
    public void addEventToStab(Long stabID, event newEvent) {
        
        stablishment stab = stabRepo.findById(stabID).orElse(null);
        //event eventAux = new event();
        event eventFlag = getEventByStabNameDate(stab, newEvent.getEventName(), newEvent.getEventDate());

        if(eventFlag != null){
            
                throw new BadRequestException("Event with name: " + newEvent.getEventName() + " and date: " + newEvent.getEventDate() + " already exists");
        
        }

        newEvent.setStablishmentID(stab);

        if(newEvent.getInterestPoints() != null){
            
            List<interestPoint> iPsToStore = new ArrayList<>();

            for(interestPoint ip : newEvent.getInterestPoints()){
                
                interestPoint interestPointAux = new interestPoint();
                interestPointAux.setStablishmentID(stab);
                interestPointAux.setEventName(newEvent);  
                interestPointAux.setXCoordinate(ip.getXCoordinate());
                interestPointAux.setYCoordinate(ip.getYCoordinate());
                interestPointAux.setDescription(ip.getDescription());
                iPsToStore.add(interestPointAux);
               
            }
            newEvent.setInterestPoints(iPsToStore);
        }
        
        eventRepo.save(newEvent);

    }

    @Transactional
    public void addPersistentEventToStab(Long stabID, int persistence, event newEvent) {

        stablishment stab = stabRepo.findById(stabID).orElse(null);
        //event eventAux = new event();
        event eventFlag = getEventByStabNameDate(stab, newEvent.getEventName(), newEvent.getEventDate());

        if(eventFlag != null){

                throw new BadRequestException("Event with name: " + newEvent.getEventName() + " and date: " + newEvent.getEventDate() + " already exists");

        }

        newEvent.setStablishmentID(stab);

        int i = 0;

        if(newEvent.getInterestPoints() != null){

            List<interestPoint> iPsToStore = new ArrayList<>();

            for(interestPoint ip : newEvent.getInterestPoints()){

                interestPoint interestPointAux = new interestPoint();
                interestPointAux.setStablishmentID(stab);
                interestPointAux.setEventName(newEvent);
                interestPointAux.setXCoordinate(ip.getXCoordinate());
                interestPointAux.setYCoordinate(ip.getYCoordinate());
                interestPointAux.setDescription(ip.getDescription());
                iPsToStore.add(interestPointAux);

            }
            newEvent.setInterestPoints(iPsToStore);
            eventRepo.save(newEvent);
            i++;
        }


        do{

            if(i == 1 && newEvent.getInterestPoints() != null){
                List<interestPoint> emptyList = new ArrayList<>();
                newEvent.setInterestPoints(emptyList);
                newEvent.setEventDate(newEvent.getEventDate().plusDays(7));
            }

            eventRepo.save(newEvent);
            newEvent.setEventDate(newEvent.getEventDate().plusDays(7));
            i++;

        }while(i < persistence);

    }
    
    //Esta version solo añade un evento a un local, no debe recibir interest points en el body ni los contempla.
    ////////////////////////////////////////////FUNCION AÑADE EVENTOS////////////////////////////////////////////
    /*@Transactional
    public void addEventToStab(Long stabID, event newEvent) {
        
        stablishment stab = stabRepo.findById(stabID).orElse(null);
        
        event eventAux = new event();
        
        eventAux = getEvent(stab, newEvent.getEventName(), newEvent.getEventDate());

        if(eventAux != null){
            throw new BadRequestException("Event with name: " + newEvent.getEventName() + " and date: " + newEvent.getEventDate() + " already exists");
        }
        
        newEvent.setStablishmentID(stab);
        
        eventRepo.save(newEvent);
        
    }*/
    ////////////////////////////////////////////FIN FUNCION AÑADE EVENTOS////////////////////////////////////////////

    public List<event> getAllEvents(stablishment stablishment) {
        return eventRepo.findAllByStablishmentID(stablishment);

    }

    public event getEventByStabNameDate(stablishment stabID, String name, LocalDate date) {
        return eventRepo.findByStablishmentIDAndEventNameAndEventDate(stabID, name, date);
    }
}
