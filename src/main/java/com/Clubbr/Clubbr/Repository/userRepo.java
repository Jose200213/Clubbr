package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userRepo extends JpaRepository<user, String> {
}
