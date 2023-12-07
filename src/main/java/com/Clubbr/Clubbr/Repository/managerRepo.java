package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.manager;
import com.Clubbr.Clubbr.Entity.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface managerRepo extends JpaRepository<manager, Long>{
    Optional<manager> findByUserID(user userID);
}
