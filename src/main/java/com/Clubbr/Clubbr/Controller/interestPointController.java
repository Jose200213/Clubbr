package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.stablishment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Service.interestPointService;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Entity.interestPoint;

import java.util.List;

@RestController
@RequestMapping("/stablishment/{stablishmentID}")
public class interestPointController {

    @Autowired
    private interestPointService interestPointService;

    @Autowired
    private stablishmentRepo stablishmentRepo;

    @PostMapping("/interestPoint/add")
    public void addInterestPointToStab(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody interestPoint newInterestPoint){
        interestPointService.addInterestPointToStab(stablishmentID, newInterestPoint);
    }

    @GetMapping("/interestPoint/all")
    public List<interestPoint> getInterestPointsByStablishment(@PathVariable("stablishmentID") Long stablishmentID){
        //TODO: usar el repo aqui creo que es una mierda, pero por ahora me sirve, he sufrido demasiado, se cambiara
        stablishment stablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
        return interestPointService.getInterestPointsByStablishment(stablishment);
    }
}
