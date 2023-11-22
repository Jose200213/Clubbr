package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface eventRepo extends JpaRepository<event, eventID> {

    //List<event> findAllByStablishmentID(int stablishmentID);
    event findByEventNameAndStablishmentID(String eventName, stablishment stablishmentID);
}
