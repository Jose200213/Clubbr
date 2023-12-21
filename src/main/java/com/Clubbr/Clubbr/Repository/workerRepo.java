package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.worker;
import com.Clubbr.Clubbr.Entity.workerID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.Entity.stablishment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface workerRepo extends JpaRepository<worker, workerID>{
    List<worker> findAllByStablishmentID(stablishment stablishment);
    Optional<worker> findByUserIDAndStablishmentID(user user, stablishment stablishment);
    List<worker> findAllByUserID(user userID);
    List<worker> findAllByEventIDIsNull();
    void deleteByUserIDAndStablishmentID(user  user, stablishment stablishment);
}
