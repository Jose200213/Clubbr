package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.ticket;
import com.Clubbr.Clubbr.Entity.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ticketRepo extends JpaRepository<ticket, Long> {

    List<ticket> findByUserID(user userID);
}
