package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.config.exception.BadRequestException;
import com.Clubbr.Clubbr.config.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.Clubbr.Clubbr.Repository.eventRepo;
import com.Clubbr.Clubbr.Service.stablishmentService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class eventService {

    @Autowired
    private eventRepo eventRepo;

    @Autowired
    private stablishmentRepo stabRepo;

    @Transactional
    public void addEventToStab(int stabID, event newEvent) {
        ///////////////// CODIGO DE JAIME ///////////////////////
        /*stablishment stablishment = stabRepo.findById(stabID).orElse(null);
        newEvent.setStablishmentID(stablishment);
        stablishment.getEvents().add(newEvent);

        stabRepo.save(stablishment);
        eventRepo.save(newEvent);

        ///////////////// FIN CODIGO DE JAIME ///////////////////////
        */
        event eventX = new event();
        if(stabRepo.existsById(stabID) == false){
            throw new NotFoundException("Stablishment with id: " + stabID + " does not exist");
        }
        stablishment stab = stabRepo.findById(stabID).orElse(null);//stabRepo.getStab(stabID);
        List<event> events = new ArrayList<>();
        events = getAllEvents(stab);  //Corregir, la comprobacion con eventAux, hay que comprobar que no haya un evento con esos params conincidentes dentro de events.

        event eventAux = new event();
        eventAux = getEvent(newEvent.getEventName(), newEvent.getEventDate());

        if(events.isEmpty()){ //Procedera a crear el nuevo evento en caso de que no haya ni un evento asignado al local
            //Procedo a crearlo
            eventX = initEvent(newEvent, stab);
            //hacer funcion para inicializar evento. Hacer otra para inicializar Interest point especificos . Lo de los trabajadores me parece q no hace falta hacerlo aqui.
        }else{
            if(eventAux != null){
                throw new BadRequestException("Event with name: " + newEvent.getEventName() + " and date: " + newEvent.getEventDate() + " already exists");
            }
            eventX = initEvent(newEvent, stab);
        }

        eventRepo.save(eventX);
    }

    private event initEvent(event newEvent, stablishment stab){
        event event = new event();
        event.setEventName(newEvent.getEventName());
        event.setEventDate(newEvent.getEventDate());
        event.setStablishmentID(stab);
        event.setEventFinishDate(newEvent.getEventFinishDate());
        event.setEventDescription(newEvent.getEventDescription());
        event.setEventTime(newEvent.getEventTime());
        if(newEvent.getInterestPoint() != null){
            event.setInterestPoint(initInterestPoints(newEvent));
        }
        return event;
    }

    private List<interestPoint> initInterestPoints(event specEvent){
        List<interestPoint> interestPointsAux = new ArrayList<>();
        for(interestPoint interestPoint : specEvent.getInterestPoint()){
            interestPoint interestPointAux = new interestPoint();
            interestPointAux.setStablishmentID(specEvent.getStablishmentID());
            interestPointAux.setEventName(specEvent);  //Primero pruebo a devolver el objeto completo
            interestPointAux.setXCoordinate(interestPoint.getXCoordinate());
            interestPointAux.setYCoordinate(interestPoint.getYCoordinate());
            interestPointAux.setDescription(interestPoint.getDescription());
            interestPointsAux.add(interestPointAux);
        }
        return interestPointsAux;
    }

    public List<event> getAllEvents(stablishment stablishment) {
        return eventRepo.findAllByStablishmentID(stablishment);

    }

    public event getEvent(String name, LocalDate date) {
        return eventRepo.findByEventNameAndEventDate(name, date);
    }
}
