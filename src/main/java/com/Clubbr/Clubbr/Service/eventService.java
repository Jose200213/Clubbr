package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.Repository.eventRepo;
import com.Clubbr.Clubbr.Repository.managerRepo;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Repository.userRepo;
import com.Clubbr.Clubbr.advice.ManagerNotFoundException;
import com.Clubbr.Clubbr.advice.ManagerNotFromStablishmentException;
import com.Clubbr.Clubbr.config.exception.BadRequestException;
import com.Clubbr.Clubbr.config.exception.NotFoundException;
import org.eclipse.paho.client.mqttv3.MqttClient;
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

    @Autowired

    private MqttClient mqttClient;

    @Autowired
    private workerService workerService;

    private jwtService jwtService;


    @Autowired
    private userRepo userRepo;


    @Autowired
    private managerRepo managerRepo;

    @Autowired
    private stablishmentService stablishmentService;


    @Transactional
    public void addEventToStab(Long stabID, event newEvent, String token) {



        stablishment stab = stabRepo.findById(stabID).orElse(null);
        user user = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
        manager manager = managerRepo.findByUserID(user).orElse(null);
        //event eventAux = new event();
        event eventFlag = getEventByStabNameDate(stabID, newEvent.getEventName(), newEvent.getEventDate());

        if(eventFlag != null){

            throw new BadRequestException("Event with name: " + newEvent.getEventName() + " and date: " + newEvent.getEventDate() + " already exists");

        }

        if (manager == null){
            throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + user.getUserID());
        }

        if (!stablishmentService.isManagerInStab(stab, manager)){
            throw new ManagerNotFromStablishmentException("El manager con el ID " + user.getUserID() + " no es manager del establecimiento con el ID " + stab.getStablishmentID());
        }

        newEvent.setStablishmentID(stab);
        newEvent.setTotalTickets(stab.getCapacity());

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


    @Transactional(readOnly = true)
    public List<event> getAllEventsOrderedByDateInStab(Long stabID) {
        stablishment stab = stabRepo.findById(stabID).orElse(null);
        return eventRepo.findAllByStablishmentIDOrderByEventDateAsc(stab);

    }

    @Transactional(readOnly = true)
    public event getEventByStabNameDate(Long stabID, String name, LocalDate date) {
        stablishment stab = stabRepo.findById(stabID).orElse(null);
        return eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, name, date);
    }

    @Transactional
    public void updateEventFromStablishment(Long stabID, String eventName, LocalDate eventDate, event targetEvent, String token) {

        stablishment stab = stabRepo.findById(stabID).orElse(null);
        event existingEvent = eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, eventName, eventDate);
        user user = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
        manager manager = managerRepo.findByUserID(user).orElse(null);

        if (existingEvent == null) {
            throw new NotFoundException("Event to update not found");
        }

        if (manager == null){
            throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + user.getUserID());
        }

        if (!stablishmentService.isManagerInStab(stab, manager)){
            throw new ManagerNotFromStablishmentException("El manager con el ID " + user.getUserID() + " no es manager del establecimiento con el ID " + stab.getStablishmentID());
        }

        event eventFlag = eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, targetEvent.getEventName(), targetEvent.getEventDate());

        if(eventFlag != null){
            throw new BadRequestException("Can't update an Event with name: " + targetEvent.getEventName() + " and date: " + targetEvent.getEventDate() + " already exists");
        }

        // Crea un nuevo evento con los datos actualizados
        event newEvent = new event();
        newEvent.setEventName(targetEvent.getEventName());
        newEvent.setEventDate(targetEvent.getEventDate());
        newEvent.setEventFinishDate(targetEvent.getEventFinishDate());
        newEvent.setEventDescription(targetEvent.getEventDescription());
        newEvent.setEventPrice(targetEvent.getEventPrice());
        newEvent.setEventTime(targetEvent.getEventTime());
        newEvent.setTotalTickets(stab.getCapacity());
        newEvent.setStablishmentID(stab);

        // Guarda el nuevo evento
        eventRepo.save(newEvent);

        // Elimina el evento existente
        eventRepo.delete(existingEvent);
    }

    @Transactional
    public void deleteEventFromStablishment(Long stabID, String eventName, LocalDate eventDate, String token) {
        stablishment stab = stabRepo.findById(stabID).orElse(null);
        event existingEvent = eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, eventName, eventDate);
        user user = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
        manager manager = managerRepo.findByUserID(user).orElse(null);

        if (existingEvent == null) {
            throw new NotFoundException("Event to delete not found");
        }

        if (manager == null){
            throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + user.getUserID());
        }

        if (!stablishmentService.isManagerInStab(stab, manager)){
            throw new ManagerNotFromStablishmentException("El manager con el ID " + user.getUserID() + " no es manager del establecimiento con el ID " + stab.getStablishmentID());
        }

        eventRepo.delete(existingEvent);
    }

    //////////////////////////////////////////// FUNCION AÑADE EVENTOS PERSISTENTES CON UNA FRECUENCIA PREDETERMINADA DE UNA SEMANA (7 DIAS) No usa Dto////////////////////////////////////////////
    @Transactional
    public void addPersistentEventToStab(Long stabID, int repeticiones, event newEvent, String token) {

        stablishment stab = stabRepo.findById(stabID).orElse(null);
        user user = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
        manager manager = managerRepo.findByUserID(user).orElse(null);
        //event eventAux = new event();
        event eventFlag = getEventByStabNameDate(stabID, newEvent.getEventName(), newEvent.getEventDate());

        if(eventFlag != null){

                throw new BadRequestException("Event with name: " + newEvent.getEventName() + " and date: " + newEvent.getEventDate() + " already exists");

        }

        if (manager == null){
            throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + user.getUserID());
        }

        if (!stablishmentService.isManagerInStab(stab, manager)){
            throw new ManagerNotFromStablishmentException("El manager con el ID " + user.getUserID() + " no es manager del establecimiento con el ID " + stab.getStablishmentID());
        }

        newEvent.setStablishmentID(stab);
        newEvent.setTotalTickets(stab.getCapacity());
        newEvent.setEventTime(stab.getOpenTime().toString());

        int i = 0;

        do{

            eventRepo.save(newEvent);
            newEvent.setEventDate(newEvent.getEventDate().plusDays(7));
            newEvent.setEventFinishDate(newEvent.getEventFinishDate().plusDays(7));
            i++;

        }while(i < repeticiones);

    }

    //////////////////////////////////////////// FIN FUNCION AÑADE EVENTOS PERSISTENTES CON UNA FRECUENCIA PREDETERMINADA DE UNA SEMANA (7 DIAS) ////////////////////////////////////////////


}
