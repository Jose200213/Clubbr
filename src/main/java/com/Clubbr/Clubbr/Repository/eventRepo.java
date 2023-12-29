package com.Clubbr.Clubbr.Repository;


import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.eventID;
import com.Clubbr.Clubbr.Entity.stablishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface eventRepo extends JpaRepository<event, eventID> {

    List<event> findAllByStablishmentIDOrderByEventDateAsc(stablishment stablishment);

    event findByStablishmentIDAndEventNameAndEventDate(@Param("stablishmentID") stablishment stabID, @Param("eventName") String name, @Param("eventDate") LocalDate date);

    Optional<event> findByEventNameAndStablishmentID(String eventName, stablishment stablishmentID);
    List<event> findAllOrderByEventDateAfterOrderByEventDateAsc(LocalDate eventDate);
}
