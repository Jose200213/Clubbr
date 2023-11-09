package com.clubbrjava.clubbr.controllers;

import com.clubbrjava.clubbr.models.Stablishment;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StablishmentController {

    @RequestMapping(value = "api/locals", method = RequestMethod.POST)
    public void addStablishment(@RequestHeader(value = "Authorization") String token){

    }

    @RequestMapping(value = "api/locals/{id}", method = RequestMethod.DELETE)
    public void deleteStablishment(@RequestHeader(value = "Authorization") String token, @PathVariable Long localID){

    }

    @RequestMapping(value = "api/locals", method = RequestMethod.GET)
    public List<Stablishment> getStablishments(@RequestHeader(value = "Authorization") String token){
        //if (!verifyToken) return null;
        //TODO: Implementar
        return null;
    }

    @RequestMapping(value = "api/locals/{id}", method = RequestMethod.GET)
    public Stablishment getStablishment(@RequestHeader(value = "Authorization") String token, @PathVariable Long localID){
        return null;
    }

    @RequestMapping(value = "api/locals/{id}", method = RequestMethod.PUT)
    public void updateStablishment(@RequestHeader(value = "Authorization") String token, @PathVariable Long localID){

    }
}
