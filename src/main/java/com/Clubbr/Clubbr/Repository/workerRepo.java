package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface workerRepo extends JpaRepository<worker, Long>{
    List<worker> findAllByStablishmentID(stablishment stablishment);

    Optional<worker> findByUserIDAndStablishmentID(user user, stablishment stablishment);
    List<worker> findAllByUserID(user userID);
    List<worker> findAllByEventIDIsNull();

    void deleteByUserIDAndStablishmentID(user  user, stablishment stablishment);

    worker findByUserID(user targetUser);

    //worker findByUserIDAndEventAndStablishmentID(user targetUser, event existingEvent, stablishment stab);  //Tal y como esta la nueva clave cuaternaria, esta forma de busqueda ya no sirve.

    worker findByUserIDAndEventAndStablishmentID(user targetUser, event event, stablishment stab);

}