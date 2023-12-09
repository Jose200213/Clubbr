package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.manager;
import com.Clubbr.Clubbr.Entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface managerRepo extends JpaRepository<manager, Long>{
    Optional<manager> findByUserID(user userID);
}
