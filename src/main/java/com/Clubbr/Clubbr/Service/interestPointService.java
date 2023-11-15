package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.interestPoint;
import com.Clubbr.Clubbr.Entity.stablishment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Clubbr.Clubbr.Repository.interestPointRepo;
import org.springframework.transaction.annotation.Transactional;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;

import java.util.List;

@Service
public class interestPointService {

    @Autowired
    private interestPointRepo interestPointRepo;

    @Autowired
    private stablishmentRepo stablishmentRepo;

    @Transactional
    public void addInterestPointToStab(Long stabID, interestPoint newInterestPoint){
        stablishment stablishment = stablishmentRepo.findById(stabID).orElse(null);

        newInterestPoint.setStablishmentID(stablishment);
        stablishment.getInterestPoints().add(newInterestPoint);

        interestPointRepo.save(newInterestPoint);
        stablishmentRepo.save(stablishment);
    }

    @Transactional(readOnly = true)
    public List<interestPoint> getInterestPointsByStablishment(stablishment stablishmentID){
        return interestPointRepo.findByStablishmentID(stablishmentID);
    }
}
