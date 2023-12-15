package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.advice.ManagerNotFoundException;
import com.Clubbr.Clubbr.advice.ManagerNotFromStablishmentException;
import com.Clubbr.Clubbr.utils.role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.Clubbr.Clubbr.Entity.manager;
import com.Clubbr.Clubbr.Repository.managerRepo;

import java.util.ArrayList;

@Service
public class managerService {

    private final managerRepo managerRepo;
    private final userService userService;
    private stablishmentService stabService;
    private final stablishmentRepo stabRepo;
    private final jwtService jwtService;

    @Autowired
    public managerService(managerRepo managerRepo, userService userService, stablishmentRepo stabRepo, jwtService jwtService) {
        this.managerRepo = managerRepo;
        this.userService = userService;
        this.stabRepo = stabRepo;
        this.jwtService = jwtService;
    }

    @Autowired
    public void setStabService(@Lazy stablishmentService stabService) {
        this.stabService = stabService;
    }

    public manager getManager(user userID){
        return managerRepo.findByUserID(userID).orElseThrow(
                () -> new ManagerNotFoundException("No se ha encontrado el manager con el ID " + userID.getUserID()
                ));
    }

    public void addOwnerToStab(Long stablishmentID, String userID){
        stablishment targetStab = stabService.getStab(stablishmentID);
        user targetUser = userService.getUser(userID);

        targetUser.setUserRole(role.MANAGER);
        manager newManager = new manager();
        newManager.setUserID(targetUser);
        newManager.setOwner(true);
        newManager.setStablishmentID(new ArrayList<>());
        newManager.getStablishmentID().add(targetStab);

        targetStab.getManagerID().add(newManager);
        managerRepo.save(newManager);
        stabRepo.save(targetStab);
    }

    public void addManagerToStab(Long stablishmentID, String userID, String token){
        String userId = jwtService.extractUserIDFromToken(token);
        stablishment targetStab = stabService.getStab(stablishmentID);
        user requestUser = userService.getUser(userId);
        user targetUser = userService.getUser(userID);

        if (userService.isManager(requestUser)){
            manager stabManager = getManager(requestUser);
            if (!isManagerInStab(targetStab, stabManager)) {
                throw new ManagerNotFromStablishmentException("El establecimiento con el ID " + targetStab.getStablishmentID() + " no pertenece al manager con el ID " + userId);
            }
        }

        targetUser.setUserRole(role.MANAGER);
        manager newManager = new manager();
        newManager.setUserID(targetUser);
        newManager.setOwner(false);
        newManager.setStablishmentID(new ArrayList<>());
        newManager.getStablishmentID().add(targetStab);

        targetStab.getManagerID().add(newManager);
        managerRepo.save(newManager);
        stabRepo.save(targetStab);
    }

    public boolean isManagerInStab(stablishment targetStab, manager stabManager) {
        return targetStab.getManagerID().contains(stabManager);
    }
}
