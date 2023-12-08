package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.panicAlert;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


public interface PanicAlertRepo extends JpaRepository<panicAlert, Long> {
    List<panicAlert> findByEventName_StablishmentIDAndUserID(stablishment stablishment, user userId);

}