package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.ticket;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.Entity.stablishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ticketRepo extends JpaRepository<ticket, Long> {

    List<ticket> findByUserID(user userID);
    boolean existsByUserIDAndEventNameAndStablishmentID(user userID, event eventName, stablishment stablishmentID);
}
