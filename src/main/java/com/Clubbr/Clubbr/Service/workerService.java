package com.Clubbr.Clubbr.Service;

import java.util.List;

import com.Clubbr.Clubbr.Entity.manager;
import com.Clubbr.Clubbr.advice.ManagerNotFoundException;
import com.Clubbr.Clubbr.advice.ManagerNotFromStablishmentException;
import com.Clubbr.Clubbr.advice.UserNotFoundException;
import com.Clubbr.Clubbr.advice.WorkerNotFoundException;
import com.Clubbr.Clubbr.utils.role;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Entity.worker;
import com.Clubbr.Clubbr.Repository.workerRepo;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Repository.userRepo;
import com.Clubbr.Clubbr.Repository.managerRepo;

@Service
public class workerService {

        @Autowired
        private workerRepo workerRepo;

        @Autowired
        private stablishmentRepo stablishmentRepo;

        @Autowired
        private userRepo userRepo;

        @Autowired
        private managerRepo managerRepo;

        @Autowired
        private jwtService jwtService;

        @Autowired
        private stablishmentService stablishmentService;

        public List<worker> getAllWorkers(Long stablishmentID, String token) {
                stablishment targetStablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
                user targetUser = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
                manager targetManager = managerRepo.findByUserID(targetUser).orElse(null);

                if (targetManager == null){
                        throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + targetUser.getUserID());
                }

                if (!stablishmentService.isManagerInStab(targetStablishment, targetManager)){
                        throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + targetStablishment.getStablishmentID());
                }

                return workerRepo.findAllByStablishmentID(targetStablishment);
        }

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
                workerToUpdate.setInterestPointID(targetWorker.getInterestPointID());
                workerToUpdate.setSalary(targetWorker.getSalary());
                workerToUpdate.setWorkingHours(targetWorker.getWorkingHours());
                workerRepo.save(workerToUpdate);
        }
}
