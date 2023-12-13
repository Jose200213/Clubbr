package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface workerRepo extends JpaRepository<worker, workerID>{
    List<worker> findAllByStablishmentID(stablishment stablishment);

    Optional<worker> findByUserIDAndStablishmentID(user user, stablishment stablishment);

    void deleteByUserIDAndStablishmentID(user  user, stablishment stablishment);

    worker findByUserID(user targetUser);

    worker findByUserIDAndEventNameAndStablishmentID(user targetUser, event existingEvent, stablishment stab);  //Tal y como esta la nueva clave cuaternaria, esta forma de busqueda ya no sirve.

    worker findByUserIDAndEventNameAndEventDateAndStablishmentID(user targetUser, String eventName, LocalDate eventDate, stablishment stab);

}