package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Entity.stablishment;
import java.util.List;
import com.Clubbr.Clubbr.Service.stablishmentService;

@RestController
@RequestMapping("/stablishment")
public class stablishmentController {

    @Autowired
    private stablishmentService stabService;

    @GetMapping("/all")
    public List<stablishment> getAllStab() {
        return stabService.getAllStab();
    }

    @GetMapping("/{id}")
    public stablishment getStab(@PathVariable int stabID) { return stabService.getStab(stabID);}

    @PostMapping("/add")
    public void addStab(@RequestBody stablishment newStab) { stabService.addStablishment(newStab);}

    @PutMapping("/update")
    public void updateStab(@RequestBody stablishment targetStab) {
        stabService.updateStab(targetStab);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStab(@PathVariable int stabID) {
        stabService.deleteStab(stabID);
    }


}


