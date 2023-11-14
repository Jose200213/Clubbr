package com.Clubbr.Clubbr.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Entity.stablishment;


@Service
public class stablishmentService {

    @Autowired
    private stablishmentRepo stabRepo;

    public List<stablishment> getAllStab() { return stabRepo.findAll();}

    public stablishment getStab(int stabID) { return stabRepo.findById(stabID).orElse(null);}

    public void deleteStab(int stabID) { stabRepo.deleteById(stabID);}

    public void addStablishment(stablishment newStab) {stabRepo.save(newStab);}

    public void updateStab(stablishment targetStab) {stabRepo.save(targetStab);}


}
