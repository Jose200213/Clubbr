package com.clubbrjava.clubbr.controllers;

import com.clubbrjava.clubbr.models.Local;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LocalController {

    @RequestMapping(value = "api/locals", method = RequestMethod.POST)
    public void addLocal(@RequestHeader(value = "Authorization") String token){

    }

    @RequestMapping(value = "api/locals/{id}", method = RequestMethod.DELETE)
    public void deleteLocal(@RequestHeader(value = "Authorization") String token, @PathVariable Long localID){

    }

    @RequestMapping(value = "api/locals", method = RequestMethod.GET)
    public List<Local> getLocals(@RequestHeader(value = "Authorization") String token){
        //if (!verifyToken) return null;
        //TODO: Implementar
        return null;
    }

    @RequestMapping(value = "api/locals/{id}", method = RequestMethod.GET)
    public Local getLocal(@RequestHeader(value = "Authorization") String token, @PathVariable Long localID){
        return null;
    }

    @RequestMapping(value = "api/locals/{id}", method = RequestMethod.PUT)
    public void updateLocal(@RequestHeader(value = "Authorization") String token, @PathVariable Long localID){

    }
}
