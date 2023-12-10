package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< Updated upstream
=======
import org.springframework.data.repository.query.Param;
>>>>>>> Stashed changes

import java.util.List;

public interface eventRepo extends JpaRepository<event, eventID> {

    //List<event> findAllByStablishmentID(int stablishmentID);
    event findByEventNameAndStablishmentID(String eventName, stablishment stablishmentID);
}
