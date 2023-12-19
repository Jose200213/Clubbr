package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.payment;
import com.Clubbr.Clubbr.Entity.worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface paymentRepo extends JpaRepository<payment, Long>{
    Optional<payment> findByWorkerID(worker worker);

}
