package com.Clubbr.Clubbr.Service;

import java.util.List;

import com.Clubbr.Clubbr.Entity.item;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Repository.itemRepo;
import com.Clubbr.Clubbr.Entity.stablishment;
import org.springframework.transaction.annotation.Transactional;


@Service
public class stablishmentService {

    @Autowired
    private stablishmentRepo stabRepo;

    @Autowired
    private itemRepo itemRepo;

    @Transactional(readOnly = true)
    public List<stablishment> getAllStab() { return stabRepo.findAll(); }

    public stablishment getStab(Long stabID) { return stabRepo.findById(stabID).orElse(null);}

    public void deleteStab(Long stabID) { stabRepo.deleteById(stabID);}

    public void addStablishment(stablishment newStab) {stabRepo.save(newStab);}

    public void updateStab(Long stablishmentID, stablishment targetStab) {
        stablishment stablishment = stabRepo.findById(stablishmentID).orElse(null);

        stablishment.setCapacity(targetStab.getCapacity());
        stablishment.setStabAddress(targetStab.getStabAddress());
        stablishment.setCloseTime(targetStab.getCloseTime());
        stablishment.setOpenTime(targetStab.getOpenTime());
        stablishment.setStabName(targetStab.getStabName());
        stabRepo.save(targetStab);
    }


}
