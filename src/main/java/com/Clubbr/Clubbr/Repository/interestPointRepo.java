package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.interestPoint;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface interestPointRepo extends JpaRepository<interestPoint, Long> {

    List<interestPoint> findByStablishmentID(stablishment stablishmentID);
}
