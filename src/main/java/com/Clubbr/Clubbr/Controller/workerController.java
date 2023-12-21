package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Clubbr.Clubbr.Service.workerService;

import java.util.List;

@RestController
@RequestMapping("stablishment/{stablishmentID}/worker")
public class workerController {

    @Autowired
    private workerService workerService;

    @GetMapping("/all")
    public List<worker> getAllWorkers(Long stablishmentID, String token) {
        return workerService.getAllWorkers(stablishmentID, token);
    }

    @GetMapping("/{userID}")
    public worker getWorker(Long stablishmentID, String userID, String token) {
        return workerService.getWorker(userID, stablishmentID, token);
    }

    @GetMapping("/update")
    public void updateWorker(worker targetWorker, String token) {
        workerService.updateWorker(targetWorker, token);
    }
}
