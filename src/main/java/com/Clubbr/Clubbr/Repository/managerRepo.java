package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.manager;
import com.Clubbr.Clubbr.Entity.user;
import org.springframework.data.jpa.repository.JpaRepository;


public interface managerRepo extends JpaRepository<manager, Long>{
    manager findByUserID(user userID);
}
