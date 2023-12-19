package com.Clubbr.Clubbr.Service;

<<<<<<< Updated upstream
import com.Clubbr.Clubbr.Repository.workerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.Clubbr.Clubbr.Entity.worker;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Repository.paymentRepo;
import com.Clubbr.Clubbr.Entity.payment;
=======
import com.Clubbr.Clubbr.Entity.payment;
import com.Clubbr.Clubbr.Entity.worker;
import com.Clubbr.Clubbr.Repository.paymentRepo;
import com.Clubbr.Clubbr.Repository.workerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
>>>>>>> Stashed changes
import java.util.List;

@Service
public class paymentService {

    @Autowired
<<<<<<< Updated upstream
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
=======
    private paymentRepo paymentRepository;

    @Autowired
    private workerRepo workerRepository;

    //Metodo para generar pagos para un usuario en particular
    public void generatePaymentForUser(String userID){
        List<worker> workers = workerRepository.findByUserID(userID);

        for (worker worker : workers){
            //Aqui calcular cuanto hay que pagarle
            float paymentAmount = calculatePayment(worker);

            //Crear objeto payment y guardarlo en la base de datos
            payment payment = new payment();
            payment.setUserID(worker.getUserID());
            payment.setAmount(paymentAmount);
            payment.setPaymentDate(new Date());

            paymentRepository.save(payment);
        }
    }

    public void generatePaymentForStablishment(Long stablishmentID){
        List<worker> workers = workerRepository.findByStablishmentID(stablishmentID);

        for (worker worker : workers){
            //Aqui calcular cuanto hay que pagarle
            float paymentAmount = calculatePayment(worker);

            //Crear objeto payment y guardarlo en la base de datos
            payment payment = new payment();
            payment.setUserID(worker.getUserID());
            payment.setAmount(paymentAmount);
            payment.setPaymentDate(new Date());

            paymentRepository.save(payment);
        }
    }

    //Metodo para generar el pago de todos los usuarios (Auto pago el 1 de cada mes)
    public void generatePaymentForAllUsers(){
        List<worker> workers = workerRepository.findAll();

        for (worker worker : workers){
            //Aqui calcular cuanto hay que pagarle
            float paymentAmount = calculatePayment(worker);

            //Crear objeto payment y guardarlo en la base de datos
            payment payment = new payment();
            payment.setUserID(worker.getUserID());
            payment.setAmount(paymentAmount);
            payment.setPaymentDate(new Date());

            paymentRepository.save(payment);
        }
    }

    private float calculatePayment(worker worker){

        return worker.getSalary() * worker.getWorkingHours();
    }
>>>>>>> Stashed changes
}
