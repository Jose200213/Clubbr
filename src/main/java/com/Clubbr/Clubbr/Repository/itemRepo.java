package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.item;
import com.Clubbr.Clubbr.Entity.stablishment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface itemRepo extends JpaRepository<item, Long> {
    List<item> findByStablishmentID(stablishment stablishmentID);
}
