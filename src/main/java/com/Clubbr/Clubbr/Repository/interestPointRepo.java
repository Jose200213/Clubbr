package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.interestPoint;
import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface interestPointRepo extends JpaRepository<interestPoint, Long> {

    List<interestPoint> findByStablishmentID(stablishment stablishmentID);

    List<interestPoint> findAllByStablishmentIDAndEventNameIsNull(stablishment stablishmentID);

    List<interestPoint> findAllByStablishmentIDAndEventNameIsNullOrStablishmentIDIsNullAndEventName(stablishment stablishmentID, event eventName);

}
