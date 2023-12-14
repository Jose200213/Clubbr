package com.Clubbr.Clubbr.Service;

import java.time.LocalDate;
import java.util.List;

import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.advice.ManagerNotFoundException;
import com.Clubbr.Clubbr.advice.ManagerNotFromStablishmentException;
import com.Clubbr.Clubbr.advice.UserNotFoundException;
import com.Clubbr.Clubbr.advice.WorkerNotFoundException;
import com.Clubbr.Clubbr.utils.role;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Repository.workerRepo;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Repository.userRepo;
import com.Clubbr.Clubbr.Repository.managerRepo;
import com.Clubbr.Clubbr.Repository.eventRepo;
import org.springframework.transaction.annotation.Transactional;

@Service
public class workerService {

        @Autowired
        private workerRepo workerRepo;

        @Autowired
        private eventRepo eventRepo;

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
            if (targetUser.getUserRole() != role.ADMIN) {
                manager manager = managerRepo.findByUserID(targetUser).orElse(null);
                if (manager == null) {
                    throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + targetUser.getUserID());
                }

                if (!stablishmentService.isManagerInStab(targetStablishment, manager)) {
                    throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + targetStablishment.getStablishmentID());
                }
            }

                return workerRepo.findAllByStablishmentID(targetStablishment);
        }

        //Metodo interno sobrecargado que usan otros metodos, no necesita securizacion porque solo se usa internamente.
        public List<worker> getAllWorkers(stablishment stablishment) {
                return workerRepo.findAllByStablishmentID(stablishment);

        }

        public worker getWorker(String userID, Long stablishmentID, String token) {
                stablishment targetStablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
                user targetUser = userRepo.findById(userID).orElse(null);
                user requestUser = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
                if (targetUser == null){
                        throw new UserNotFoundException("No se ha encontrado el usuario con el ID " + targetUser.getUserID());
                }

                if (requestUser.getUserRole() == role.WORKER){
                        if (!requestUser.getUserID().equals(userID)){
                                throw new UserNotFoundException("No se ha encontrado el usuario con el ID " + targetUser.getUserID());
                        }
                        return workerRepo.findByUserIDAndStablishmentID(targetUser, targetStablishment).orElse(null);
                }

                manager targetManager = managerRepo.findByUserID(requestUser).orElse(null);
                if (targetManager == null){
                        throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + targetUser.getUserID());
                }
                if (!stablishmentService.isManagerInStab(targetStablishment, targetManager)){
                        throw new ManagerNotFromStablishmentException("El manager con el ID " + requestUser.getUserID() + " no es manager del establecimiento con el ID " + targetStablishment.getStablishmentID());
                }
                return workerRepo.findByUserIDAndStablishmentID(targetUser, targetStablishment).orElse(null);
        }

        public void updateWorker(Long stablishmentID, worker targetWorker, String token) {
                user targetUser = userRepo.findById(jwtService.extractUserIDFromToken(token)).orElse(null);
                manager targetManager = managerRepo.findByUserID(targetUser).orElse(null);
                stablishment targetStablishment = stablishmentRepo.findById(stablishmentID).orElse(null);
                if (targetManager == null){
                        throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + targetUser.getUserID());
                }

                if (!stablishmentService.isManagerInStab(targetStablishment, targetManager)){
                        throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + targetWorker.getStablishmentID().getStablishmentID());
                }

                worker workerToUpdate = workerRepo.findByUserIDAndStablishmentID(targetWorker.getUserID(), targetStablishment).orElse(null);
                if (workerToUpdate == null){
                        throw new WorkerNotFoundException("No se ha encontrado el usuario con el ID " + targetWorker.getUserID());
                }
                workerToUpdate.setInterestPointID(targetWorker.getInterestPointID());
                workerToUpdate.setSalary(targetWorker.getSalary());
                workerToUpdate.setWorkingHours(targetWorker.getWorkingHours());
                workerRepo.save(workerToUpdate);
        }


        @Transactional
        public void updateAttendance(String telegramID, boolean attendance, String eventName, LocalDate eventDate, Long stabID) {

                user targetUser = userRepo.findByTelegramID(Long.parseLong(telegramID));
                stablishment stab = stablishmentRepo.findById(stabID).orElse(null);
                event existingEvent = eventRepo.findByStablishmentIDAndEventNameAndEventDate(stab, eventName, eventDate);

                worker workerToUpdate = workerRepo.findByUserIDAndEventAndStablishmentID(targetUser, existingEvent, stab);
                workerToUpdate.setAttendance(attendance);
                workerRepo.save(workerToUpdate);

        }
}
