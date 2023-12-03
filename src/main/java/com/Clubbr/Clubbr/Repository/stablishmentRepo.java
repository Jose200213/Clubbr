package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.manager;
import com.Clubbr.Clubbr.Entity.stablishment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface stablishmentRepo extends JpaRepository<stablishment, Long>{
    List<stablishment> findByManagerID(manager managerID);
}
