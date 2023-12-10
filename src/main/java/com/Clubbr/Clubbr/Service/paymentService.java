package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Repository.workerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.Clubbr.Clubbr.Entity.worker;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Repository.paymentRepo;
import com.Clubbr.Clubbr.Entity.payment;
import java.util.List;

@Service
public class paymentService {

    @Autowired
    private workerRepo workerRepo;

    @Autowired
    private paymentRepo paymentRepo;

    @Transactional
    public void actualizarPagos(){
        List<worker> workers = workerRepo.findAll();

        for (worker worker : workers){
            float salario = worker.getSalary();
            float horasTrabajadas = worker.getWorkingHours();
            float nuevoPago = salario * horasTrabajadas;

            payment payment = worker.getPayment();
            if (payment == null){
                payment = new payment();
                payment.setWorker(worker);
            }
            payment.setFinalPayment(nuevoPago);
            paymentRepo.save(payment);
        }
    }

    @Transactional
    public void actualizarPagosLocal(stablishment stablishment){
        List<worker> workers = workerRepo.findAllByStablishmentID(stablishment);

        for (worker worker : workers){
            float salario = worker.getSalary();
            float horasTrabajadas = worker.getWorkingHours();
            float nuevoPago = salario * horasTrabajadas;

            payment payment = worker.getPayment();
            if (payment == null){
                payment = new payment();
                payment.setWorker(worker);
            }
            payment.setFinalPayment(nuevoPago);
            paymentRepo.save(payment);
        }
    }
}
