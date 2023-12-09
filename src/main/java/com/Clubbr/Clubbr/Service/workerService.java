package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.Entity.worker;
import com.Clubbr.Clubbr.Repository.interestPointRepo;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Repository.userRepo;
import com.Clubbr.Clubbr.Repository.workerRepo;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class workerService {

        @Autowired
        private workerRepo workerRepo;

        @Autowired
        private stablishmentRepo stabRepo;

        @Autowired
        private userRepo userRepo;

        @Autowired
        private interestPointRepo interestPointRepo;

        @Autowired
        private MqttClient mqttClient;

        public List<worker> getAllWorkers(stablishment stablishment) {
                return workerRepo.findAllByStablishmentID(stablishment);

        }
        public worker getWorker(user user, stablishment stablishment) {
                return workerRepo.findByUserIDAndStablishmentID(user, stablishment);
        }
        public void deleteWorker(user user, stablishment stablishment) {
                workerRepo.deleteByUserIDAndStablishmentID(user, stablishment);
        }


        @Transactional
        public void addWorker(worker newWorker) {
                workerRepo.save(newWorker);

        }
        public void updateWorker(worker targetWorker) {workerRepo.save(targetWorker);
        }


}
