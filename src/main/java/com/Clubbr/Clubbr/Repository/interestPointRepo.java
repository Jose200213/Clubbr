package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.interestPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
public interface interestPointRepo extends JpaRepository<interestPoint, Integer> {
=======
import java.util.List;

@Repository
public interface interestPointRepo extends JpaRepository<interestPoint, Long> {

    List<interestPoint> findByStablishmentID(stablishment stablishmentID);
    List<interestPoint> findByEventName(event eventName);

>>>>>>> PruebaMerge06-12-2023
}
