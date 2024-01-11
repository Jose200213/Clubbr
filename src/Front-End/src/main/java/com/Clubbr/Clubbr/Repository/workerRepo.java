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
    List<worker> findAllByEventID(event eventID);

    List<worker> findAllByStablishmentIDAndEventIDOrStablishmentIDAndEventIDIsNull(stablishment stabID, event eventID, stablishment stabID2);
    Optional<worker> findByEventIDAndUserID(event eventID, user userID);

    void deleteByUserIDAndStablishmentID(user  user, stablishment stablishment);

    worker findByUserID(user targetUser);


    worker findByUserIDAndEventIDAndStablishmentID(user targetUser, event eventID, stablishment stab);
    boolean existsByUserIDAndEventIDAndStablishmentID(user targetUser, event eventID, stablishment stab);

}