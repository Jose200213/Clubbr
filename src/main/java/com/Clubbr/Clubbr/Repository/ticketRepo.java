package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ticketRepo extends JpaRepository<ticket, Integer> {

<<<<<<< HEAD
=======
@Repository
public interface ticketRepo extends JpaRepository<ticket, Long> {

    List<ticket> findByUserID(user userID);
>>>>>>> PruebaMerge06-12-2023
}
