package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.Repository.workerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.Clubbr.Clubbr.Entity.worker;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Repository.paymentRepo;
import com.Clubbr.Clubbr.Entity.payment;


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
            payment.setAmount(paymentAmount);
            payment.setPaymentDate(new Date());
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
            payment.setPaymentDate(new Date());
            payment.setPaid(false);

            paymentRepository.save(payment);
        }
    }

    //Metodo para generar el pago de todos los usuarios (Auto pago el 1 de cada mes)
    public void generatePaymentForStabUsers(){
        List<worker> workers = workerService.getWorkersWithNullEventID();

        for (worker worker : workers){
            //Aqui calcular cuanto hay que pagarle
            float paymentAmount = calculatePayment(worker);

            //Crear objeto payment y guardarlo en la base de datos
            payment payment = new payment();
            payment.setWorkerID(worker);
            payment.setAmount(paymentAmount);
            payment.setPaymentDate(new Date());
            payment.setPaid(false);

            paymentRepository.save(payment);
        }
    }

    public void generatePaymentForAllUsers(){
        List<worker> workers = workerService.getAllWorkers();

        for (worker worker : workers){
            //Aqui calcular cuanto hay que pagarle
            payment payment = paymentRepository.findByWorkerIDAndPaymentDate(worker, new Date());

            if (payment == null){
                float paymentAmount = calculatePayment(worker);

                //Crear objeto payment y guardarlo en la base de datos
                payment newPayment = new payment();
                newPayment.setWorkerID(worker);
                newPayment.setAmount(paymentAmount);
                newPayment.setPaymentDate(new Date());
                newPayment.setPaid(false);

                paymentRepository.save(newPayment);
            }else{
                payment.setAmount(calculatePayment(worker));
                paymentRepository.save(payment);
            }
        }

    }

    private float calculatePayment(worker worker){

        return worker.getSalary() * worker.getWorkingHours();
    }
}