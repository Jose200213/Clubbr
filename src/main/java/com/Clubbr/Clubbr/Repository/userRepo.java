package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.Entity.worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface userRepo extends JpaRepository<user, String> {
    user findByTelegramID(@Param("TelegramID") Long telegramID);
    user findByUserID(@Param("userID") String userId);
}
