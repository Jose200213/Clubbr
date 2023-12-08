package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface userRepo extends JpaRepository<user, String> {

    user findByUserID(@Param("userID") String userId);
}
