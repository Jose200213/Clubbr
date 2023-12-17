package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.Repository.eventRepo;
import com.Clubbr.Clubbr.advice.ResourceNotFoundException;
import com.Clubbr.Clubbr.config.exception.BadRequestException;
import com.Clubbr.Clubbr.config.exception.NotFoundException;
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
    private jwtService jwtService;

    @Autowired
    private userService userService;

    @Autowired
    private managerService managerService;

    @Autowired
    private stablishmentService stablishmentService;

    //Esta version añade un evento a un local y añade interest points especificos al evento si y solo si los hay en el body.
    //La funcion debe tener manejada si o si la excepcion de eventos con el mismo nombre y fecha pues de no ser asi,
    //jpa no detecta que haya error y al insertar el evento con el mismo nombre y fecha que otro, lo trata como un update
    //y no como un insert, por lo que no se lanza excepcion (restriccion clave primaria) y se actualiza el evento existente y
    //se añaden los interest points (si los hay en el body) al mismo (Todo esto sin añadir un nuevo evento a la DB, si no solo actualizarlo => interest points replicados en DB)
    //////////////////////////////////////////// FUNCION AÑADE EVENTOS E INTEREST_POINTS (OPCIONAL) ////////////////////////////////////////////
    @Transactional
    public void addEventToStab(Long stabID, event newEvent, String token) {
        stablishment stab = stablishmentService.getStab(stabID);
        user user = userService.getUser(jwtService.extractUserIDFromToken(token));
        event eventFlag = getEventByStabNameDate(stabID, newEvent.getEventName(), newEvent.getEventDate());

        if (eventFlag != null) {

            throw new BadRequestException("Event with name: " + newEvent.getEventName() + " and date: " + newEvent.getEventDate() + " already exists");

        }

        if (userService.isManager(user)) {
            manager manager = managerService.getManager(user);
            if (!managerService.isManagerInStab(stab, manager)) {
                throw new ResourceNotFoundException("Manager", "userID", user.getUserID(), "Establecimiento", "stablishmentID", stab.getStablishmentID());
            }
        }

        newEvent.setStablishmentID(stab);
        newEvent.setTotalTickets(stab.getCapacity());

        if (newEvent.getInterestPoints() != null) {

            List<interestPoint> iPsToStore = new ArrayList<>();

            for (interestPoint ip : newEvent.getInterestPoints()) {

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


//Añade eventos persistentes con una frecuencia y un numero de repeticiones recibidos en el body y recuperados en el dto eventWithPersistenceDto.
    /*@Transactional
    public void addPersistentEventToStab(Long stabID, eventWithPersistenceDto newEventDto) {
        event newEvent = new event();
        stablishment stab = stabRepo.findById(stabID).orElse(null);
        //event eventAux = new event();
        event eventFlag = getEventByStabNameDate(stabID, newEvent.getEventName(), newEvent.getEventDate());

        if(eventFlag != null){

            throw new BadRequestException("Event with name: " + newEvent.getEventName() + " and date: " + newEvent.getEventDate() + " already exists");

        }

        newEvent.setStablishmentID(stab);
        newEvent.setTotalTickets(stab.getCapacity());
        newEvent.setEventName(newEventDto.getEventName());
        newEvent.setEventDate(newEventDto.getEventDate());
        newEvent.setEventDescription(newEventDto.getEventDescription());
        newEvent.setEventTime(stab.getOpenTime().toString());
        newEvent.setEventFinishDate(newEventDto.getEventFinishDate());

        int i = 0;

        do{

            eventRepo.save(newEvent);
            newEvent.setEventDate(newEvent.getEventDate().plusDays(newEventDto.getFrecuencia()));
            newEvent.setEventFinishDate(newEvent.getEventFinishDate().plusDays(newEventDto.getFrecuencia()));
            i++;

        }while(i < newEventDto.getRepeticiones());

    }*/
//////////////////////////////////////////// FIN FUNCION AÑADE EVENTOS E INTEREST_POINTS (OPCIONAL) ////////////////////////////////////////////

    @Transactional(readOnly = true)
    public List<event> getAllEventsOrderedByDateInStab(Long stabID) {
        stablishment stab = stablishmentService.getStab(stabID);
        return eventRepo.findAllByStablishmentIDOrderByEventDateAsc(stab);
    }

    @Transactional(readOnly = true)
    public event getEventByStabNameDate(Long stabID, String name, LocalDate date) {
        stablishment stab = stablishmentService.getStab(stabID);
        return eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, name, date);
    }

    public event getEventByEventNameAndStablishmentID(String eventName, stablishment stablishmentID) {
        return eventRepo.findByEventNameAndStablishmentID(eventName, stablishmentID)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", "eventName", eventName, "Establecimiento", "stablishmentID", stablishmentID.getStablishmentID()));
    }

    @Transactional
    public void updateEventFromStablishment(Long stabID, String eventName, LocalDate eventDate, event targetEvent, String token) {

        stablishment stab = stablishmentService.getStab(stabID);
        event existingEvent = eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, eventName, eventDate);
        user user = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (existingEvent == null) {
            throw new NotFoundException("Event to update not found");
        }

        if (userService.isManager(user)) {
            manager manager = managerService.getManager(user);
            if (!managerService.isManagerInStab(stab, manager)) {
                throw new ResourceNotFoundException("Manager", "userID", user.getUserID(), "Establecimiento", "stablishmentID", stab.getStablishmentID());
            }
        }

        event eventFlag = eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, targetEvent.getEventName(), targetEvent.getEventDate());

        if (eventFlag != null) {
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
        stablishment stab = stablishmentService.getStab(stabID);
        event existingEvent = eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, eventName, eventDate);
        user user = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (existingEvent == null) {
            throw new NotFoundException("Event to delete not found");
        }

        if (userService.isManager(user)) {
            manager manager = managerService.getManager(user);
            if (!managerService.isManagerInStab(stab, manager)) {
                throw new ResourceNotFoundException("Manager", "userID", user.getUserID(), "Establecimiento", "stablishmentID", stab.getStablishmentID());
            }
        }

        eventRepo.delete(existingEvent);
    }

    //////////////////////////////////////////// FUNCION AÑADE EVENTOS PERSISTENTES CON UNA FRECUENCIA PREDETERMINADA DE UNA SEMANA (7 DIAS) No usa Dto////////////////////////////////////////////
    @Transactional
    public void addPersistentEventToStab(Long stabID, int repeticiones, event newEvent, String token) {
        stablishment stab = stablishmentService.getStab(stabID);
        user user = userService.getUser(jwtService.extractUserIDFromToken(token));
        //event eventAux = new event();
        event eventFlag = getEventByStabNameDate(stabID, newEvent.getEventName(), newEvent.getEventDate());

        if (eventFlag != null) {

            throw new BadRequestException("Event with name: " + newEvent.getEventName() + " and date: " + newEvent.getEventDate() + " already exists");

        }

        if (userService.isManager(user)) {
            manager manager = managerService.getManager(user);
            if (!managerService.isManagerInStab(stab, manager)) {
                throw new ResourceNotFoundException("Manager", "userID", user.getUserID(), "Establecimiento", "stablishmentID", stab.getStablishmentID());
            }
        }

        newEvent.setStablishmentID(stab);
        newEvent.setTotalTickets(stab.getCapacity());
        newEvent.setEventTime(stab.getOpenTime().toString());

        int i = 0;

        do {

            eventRepo.save(newEvent);
            newEvent.setEventDate(newEvent.getEventDate().plusDays(7));
            newEvent.setEventFinishDate(newEvent.getEventFinishDate().plusDays(7));
            i++;

        } while (i < repeticiones);

    }

//////////////////////////////////////////// FIN FUNCION AÑADE EVENTOS PERSISTENTES CON UNA FRECUENCIA PREDETERMINADA DE UNA SEMANA (7 DIAS) ////////////////////////////////////////////

//Esta version solo añade un evento  un local, no debe recibir interest points en el body ni los contempla.
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
}

