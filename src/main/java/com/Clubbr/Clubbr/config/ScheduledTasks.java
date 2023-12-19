package com.Clubbr.Clubbr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.Clubbr.Clubbr.Service.paymentService;

@Component
public class ScheduledTasks {

    @Autowired
    private paymentService paymentService;

    //Ejecutar al comienzo de cada mes a las 00:00
    @Scheduled(cron = "0 0 0 1 * ?")
    public void generatePaymentForAllUsers(){
        paymentService.generatePaymentForAllUsers();
    }
}
