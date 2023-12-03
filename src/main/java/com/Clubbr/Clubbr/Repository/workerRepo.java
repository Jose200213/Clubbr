package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.worker;
import com.Clubbr.Clubbr.Entity.workerID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.Entity.stablishment;

import java.util.List;

public interface workerRepo extends JpaRepository<worker, workerID>{
    List<worker> findAllByStablishmentID(stablishment stablishment);
    worker findByUserIDAndStablishmentID(user user, stablishment stablishment);
    void deleteByUserIDAndStablishmentID(user  user, stablishment stablishment);
}
