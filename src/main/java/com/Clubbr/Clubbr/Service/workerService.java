package com.Clubbr.Clubbr.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Entity.worker;
import com.Clubbr.Clubbr.Repository.workerRepo;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Repository.userRepo;

@Service
public class workerService {

        @Autowired
        private workerRepo workerRepo;

        @Autowired
        private stablishmentRepo stablishmentRepo;

        @Autowired
        private userRepo userRepo;

        public List<worker> getAllWorkers(Long stablishmentID, String token) {
                stablishment targetStablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
                return workerRepo.findAllByStablishmentID(targetStablishment);
        }

        //TODO: Si el usuario es worker, comprobar que es el mismo
        //TODO: Si el usuario es manager, comprobar que es su stablihment
        public worker getWorker(String userID, Long stablishmentID, String token) {
                stablishment targetStablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
                user targetUser = userRepo.findById(userID).orElse(null);
                return workerRepo.findByUserIDAndStablishmentID(targetUser, targetStablishment).orElse(null);
        }

        public void updateWorker(worker targetWorker, String token) {workerRepo.save(targetWorker);
        }
}
