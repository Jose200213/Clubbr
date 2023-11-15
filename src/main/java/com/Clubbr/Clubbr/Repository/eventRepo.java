package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.stablishment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface eventRepo extends JpaRepository<event, Integer> {

    //List<event> findAllByStablishmentID(int stablishmentID);
}
