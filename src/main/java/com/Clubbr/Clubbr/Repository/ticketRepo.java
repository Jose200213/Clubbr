package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ticketRepo extends JpaRepository<ticket, Integer> {

}
