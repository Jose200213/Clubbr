package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.payment;
import com.Clubbr.Clubbr.Entity.worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface paymentRepo extends JpaRepository<payment, Long> {
    payment findByWorkerIDAndPaymentDate(worker workerID, Date paymentDate);
}
