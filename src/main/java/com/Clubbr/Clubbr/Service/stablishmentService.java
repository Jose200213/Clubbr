package com.Clubbr.Clubbr.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.Clubbr.Clubbr.Entity.worker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Service.workerService;


@Service
public class stablishmentService {

    @Autowired
    private stablishmentRepo stabRepo;



    public List<stablishment> getAllStab() {
        //stablishment stablishment = new stablishment();
        //stablishment.getEvents().
        return stabRepo.findAll();
    }

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
