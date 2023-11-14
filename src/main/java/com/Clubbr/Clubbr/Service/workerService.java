package com.Clubbr.Clubbr.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Entity.worker;
import com.Clubbr.Clubbr.Repository.workerRepo;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.user;

@Service
public class workerService {

        @Autowired
        private workerRepo workerRepo;

        public List<worker> getAllWorkers(stablishment stablishment) {
                return workerRepo.findAllByStablishmentID(stablishment);

        }
        public worker getWorker(user user, stablishment stablishment) {
                return workerRepo.findByUserIDAndStablishmentID(user, stablishment);
        }
        public void deleteWorker(user user, stablishment stablishment) {
                workerRepo.deleteByUserIDAndStablishmentID(user, stablishment);
        }
        public void addWorker(worker newWorker) {workerRepo.save(newWorker);
        }
        public void updateWorker(worker targetWorker) {workerRepo.save(targetWorker);
        }

}
