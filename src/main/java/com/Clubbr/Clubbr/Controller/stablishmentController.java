package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Service.eventService;
import com.Clubbr.Clubbr.Service.stablishmentService;
import com.Clubbr.Clubbr.Service.workerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stablishment")
public class stablishmentController {

    @Autowired
    private stablishmentService stabService;


    @GetMapping("/all")
    public List<stablishment> getAllStab() {
        return stabService.getAllStab();
    }

    @GetMapping("/{stablishmentID}")
    public stablishment getStab(@PathVariable Long stablishmentID) { return stabService.getStab(stablishmentID);}

    @PostMapping("/add")
    public void addStab(@RequestBody stablishment newStab) { stabService.addStablishment(newStab); }

    @PutMapping("/update/{stablishmentID}")
    public void updateStab(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody stablishment targetStab) {
        stabService.updateStab(stablishmentID, targetStab);
    }

    @DeleteMapping("/delete/{stablishmentID}")
    public void deleteStab(@PathVariable("stablishmentID") Long stablishmentID) {
        stabService.deleteStab(stablishmentID);
    }

}



