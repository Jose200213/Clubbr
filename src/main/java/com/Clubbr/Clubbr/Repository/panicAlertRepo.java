package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.panicAlert;
import com.Clubbr.Clubbr.Entity.stablishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface panicAlertRepo extends JpaRepository<panicAlert, Long> {


    List<panicAlert> findAllByStablishmentID(@Param("stablishmentID") stablishment stab);
}