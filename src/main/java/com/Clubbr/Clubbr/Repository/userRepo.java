package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.repository.query.Param;
=======
import org.springframework.stereotype.Repository;
>>>>>>> PruebaMerge06-12-2023

import java.util.Optional;

@Repository
public interface userRepo extends JpaRepository<user, String> {

    user findByUserID(@Param("userID") String userId);
}
