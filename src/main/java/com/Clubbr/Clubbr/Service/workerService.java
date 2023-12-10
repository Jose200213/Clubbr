package com.Clubbr.Clubbr.Service;

import java.util.List;

<<<<<<< Updated upstream
=======
import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.advice.ManagerNotFoundException;
import com.Clubbr.Clubbr.advice.ManagerNotFromStablishmentException;
import com.Clubbr.Clubbr.advice.UserNotFoundException;
import com.Clubbr.Clubbr.advice.WorkerNotFoundException;
import com.Clubbr.Clubbr.utils.role;
>>>>>>> Stashed changes
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Repository.workerRepo;
<<<<<<< Updated upstream
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.user;
=======
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Repository.userRepo;
import com.Clubbr.Clubbr.Repository.managerRepo;
>>>>>>> Stashed changes

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

<<<<<<< Updated upstream
=======
        public worker getWorker(String userID, Long stablishmentID, String token) {
                stablishment targetStablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
                user targetUser = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
                if (targetUser == null){
                        throw new UserNotFoundException("No se ha encontrado el usuario con el ID " + targetUser.getUserID());
                }

                if (targetUser.getUserRole() == role.WORKER){
                        if (!targetUser.getUserID().equals(userID)){
                                throw new UserNotFoundException("No se ha encontrado el usuario con el ID " + targetUser.getUserID());
                        }
                        return workerRepo.findByUserIDAndStablishmentID(targetUser, targetStablishment).orElse(null);
                }

                manager targetManager = managerRepo.findByUserID(targetUser).orElse(null);
                if (targetManager == null){
                        throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + targetUser.getUserID());
                }
                if (!stablishmentService.isManagerInStab(targetStablishment, targetManager)){
                        throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + targetStablishment.getStablishmentID());
                }
                return workerRepo.findByUserIDAndStablishmentID(targetUser, targetStablishment).orElse(null);
        }

        public void updateWorker(worker targetWorker, String token) {
                user targetUser = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
                manager targetManager = managerRepo.findByUserID(targetUser).orElse(null);
                float finalSalary = calculateFinalSalary(targetWorker);
                if (targetManager == null){
                        throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + targetUser.getUserID());
                }

                if (!stablishmentService.isManagerInStab(targetWorker.getStablishmentID(), targetManager)){
                        throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + targetWorker.getStablishmentID().getStablishmentID());
                }

                worker workerToUpdate = workerRepo.findByUserIDAndStablishmentID(targetWorker.getUserID(), targetWorker.getStablishmentID()).orElse(null);
                if (workerToUpdate == null){
                        throw new WorkerNotFoundException("No se ha encontrado el usuario con el ID " + targetWorker.getUserID());
                }

                workerToUpdate.setWorkingHours(targetWorker.getWorkingHours());
                workerToUpdate.setInterestPointID(targetWorker.getInterestPointID());
                workerToUpdate.setSalary(targetWorker.getSalary());
                workerToUpdate.setWorkingHours(targetWorker.getWorkingHours());
                workerRepo.save(workerToUpdate);
        }


        //Cosas relacionadas con los pagos de workers
        private float calculateFinalSalary(worker worker) {
                return worker.getSalary() * worker.getWorkingHours();
        }
>>>>>>> Stashed changes
}
