package com.Clubbr.Clubbr.Repository;

import com.Clubbr.Clubbr.Entity.item;
import com.Clubbr.Clubbr.Entity.stablishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface itemRepo extends JpaRepository<item, Long> {
    List<item> findByStablishmentID(stablishment stablishmentID);
}
