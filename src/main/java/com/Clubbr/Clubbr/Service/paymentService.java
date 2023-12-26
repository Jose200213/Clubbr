package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.Repository.workerRepo;
import com.Clubbr.Clubbr.advice.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.Clubbr.Clubbr.Repository.paymentRepo;


import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@Service
public class paymentService {

    @Autowired
    private paymentRepo paymentRepository;

    @Autowired
    private workerRepo workerRepository;

    @Autowired
    private workerService workerService;

    @Autowired
    private eventService eventService;

    @Autowired
    private stablishmentService stablishmentService;

    @Autowired
    private userService userService;

    //Metodo para generar pagos para un usuario en particular
    public void generatePaymentForUser(String userID){
        user targetUser = userService.getUser(userID);
        List<worker> workers = workerService.getAllWorkersFromUser(targetUser);

        for (worker worker : workers){
            //Aqui calcular cuanto hay que pagarle
            float paymentAmount = calculatePayment(worker);

            //Crear objeto payment y guardarlo en la base de datos
            payment payment = new payment();
            payment.setWorkerID(worker);
            payment.setStablishmentID(worker.getStablishmentID());
            payment.setEventID(worker.getEventID());
            payment.setAmount(paymentAmount);
            payment.setPaymentDate(YearMonth.now());
            payment.setPaid(false);

            paymentRepository.save(payment);
        }
    }

    public void generatePaymentForStablishment(Long stablishmentID, String token){
        List<worker> workers = workerService.getAllWorkersFromStab(stablishmentID, token);

        for (worker worker : workers){
            //Aqui calcular cuanto hay que pagarle
            float paymentAmount = calculatePayment(worker);

            //Crear objeto payment y guardarlo en la base de datos
            payment payment = new payment();
            payment.setWorkerID(worker);
            payment.setAmount(paymentAmount);
            payment.setPaymentDate(YearMonth.now());
            payment.setPaid(false);

            paymentRepository.save(payment);
        }
    }

    //Metodo para generar el pago de todos los usuarios (Auto pago el 1 de cada mes)
    //@Scheduled(cron = "0 0 0 1 * ?")
    public void generatePaymentForStabUsers(){
        List<worker> workers = workerService.getWorkersWithNullEventID();

        for (worker worker : workers){
            //Aqui calcular cuanto hay que pagarle
            float paymentAmount = calculatePayment(worker);

            //Crear objeto payment y guardarlo en la base de datos
            payment payment = new payment();
            payment.setWorkerID(worker);
            payment.setAmount(paymentAmount);
            payment.setPaymentDate(YearMonth.now());
            payment.setPaid(false);

            paymentRepository.save(payment);
        }
    }

    //@Scheduled(fixedRate = 43200000) //Cada 12 horas
    public void generatePaymentForAllUsers(){
        List<worker> workers = workerService.getAllWorkers();

        for (worker worker : workers){
            //Aqui calcular cuanto hay que pagarle
            payment payment = paymentRepository.findByWorkerIDAndPaymentDate(worker, YearMonth.now());

            if (payment == null){
                float paymentAmount = calculatePayment(worker);

                //Crear objeto payment y guardarlo en la base de datos
                payment newPayment = new payment();
                newPayment.setWorkerID(worker);
                newPayment.setAmount(paymentAmount);
                newPayment.setPaymentDate(YearMonth.now());
                newPayment.setPaid(false);

                paymentRepository.save(newPayment);
            }else{
                payment.setAmount(calculatePayment(worker));
                paymentRepository.save(payment);
            }
        }
    }

    public List<payment> getPaymentByUserID(String userID){
        user targetUser = userService.getUser(userID);
        return paymentRepository.findByWorkerID_UserIDAndPaymentDate(targetUser, YearMonth.now());
    }

    public List<payment> getPaymentByStab(Long stablishmentID){
        stablishment targetStab = stablishmentService.getStab(stablishmentID);
        return paymentRepository.findByStablishmentIDAndPaymentDate(targetStab, YearMonth.now());
    }

    public List<payment> getPaymentByEvent(Long stablishmentID, String eventName){
        stablishment targetStab = stablishmentService.getStab(stablishmentID);
        event targetEvent = eventService.getEventByEventNameAndStablishmentID(eventName, targetStab);
        return paymentRepository.findByEventIDAndPaymentDate(targetEvent, YearMonth.now());
    }

    public payment getPaymentById(Long paymentID){
        return paymentRepository.findById(paymentID).orElseThrow(() -> new ResourceNotFoundException("Pago", "paymentID", paymentID));
    }

    public void payPayment(Long paymentID){
        payment payment = getPaymentById(paymentID);
        payment.setPaid(true);
        paymentRepository.save(payment);
    }

    private float calculatePayment(worker worker){

        return worker.getSalary() * worker.getWorkingHours();
    }
}