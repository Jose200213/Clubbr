package com.Clubbr.Clubbr.Service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.Clubbr.Clubbr.Entity.interestPoint;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.dto.workerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Entity.worker;
import com.Clubbr.Clubbr.Repository.workerRepo;
import com.Clubbr.Clubbr.Repository.interestPointRepo;
import com.Clubbr.Clubbr.Repository.userRepo;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.user;
import org.springframework.transaction.annotation.Transactional;

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


        public void updateAttendance(String telegramID, boolean attendance) {
                // Buscas el usuario con el telegramID que recibiste
                user targetUser = userRepo.findByTelegramID(Long.parseLong(telegramID));
                worker worker = workerRepo.findByUserID(targetUser);

                // Actualizas el valor del campo attendance
                worker.setAttendance(attendance);

                // Guardas el worker actualizado en la base de datos
                workerRepo.save(worker);
        }

}
