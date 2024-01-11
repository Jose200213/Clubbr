package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.payment;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.Entity.worker;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@Repository
public interface paymentRepo extends JpaRepository<payment, Long> {
    payment findByWorkerIDAndPaymentDate(worker workerID, YearMonth paymentDate);
    List<payment> findByWorkerID_UserIDAndPaymentDate(user userID, YearMonth paymentDate);
    List<payment> findByStablishmentIDAndPaymentDate(stablishment stablishmentID, YearMonth paymentDate);
    List<payment> findByEventIDAndPaymentDate(event eventID, YearMonth paymentDate);
}
