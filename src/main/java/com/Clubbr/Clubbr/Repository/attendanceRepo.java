package com.Clubbr.Clubbr.Repository;
import com.Clubbr.Clubbr.Entity.attendance;
import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface attendanceRepo extends JpaRepository<attendance, Long> {

    attendance findByUserIDAndEventNameAndStablishmentID(user targetUser, event existingEvent, stablishment stab);

}
