package com.Clubbr.Clubbr.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.advice.*;
import com.Clubbr.Clubbr.utils.role;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import org.springframework.transaction.annotation.Transactional;
import com.Clubbr.Clubbr.Repository.managerRepo;
import com.Clubbr.Clubbr.Repository.userRepo;
import com.Clubbr.Clubbr.Repository.workerRepo;
import com.Clubbr.Clubbr.Repository.interestPointRepo;


/**
 * Servicio para gestionar los establecimientos.
 */
@Service
public class stablishmentService {

    private final stablishmentRepo stabRepo;

    private final jwtService jwtService;

    private managerService managerService;

    private final userService userService;

    @Autowired
    public stablishmentService(stablishmentRepo stabRepo, jwtService jwtService, userService userService) {
        this.stabRepo = stabRepo;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Autowired
    public void setManagerService(@Lazy managerService managerService) {
        this.managerService = managerService;
    }


    /**
     * Obtiene todos los establecimientos.
     * @return una lista de todos los establecimientos.
     */
    @Transactional(readOnly = true)
    public List<stablishment> getAllStab() {
        return stabRepo.findAll();
    }

    /**
     * Obtiene un establecimiento por su ID.
     * @param stabID el ID del establecimiento.
     * @return el establecimiento con el ID proporcionado.
     * @throws StablishmentNotFoundException si no se encuentra el establecimiento.
     */
    public stablishment getStab(Long stabID) {
        return stabRepo.findById(stabID)
                .orElseThrow(() -> new StablishmentNotFoundException("No se ha encontrado el establecimiento con el ID " + stabID));
    }

    /**
     * Obtiene todos los establecimientos de un manager.
     * @param token el token del manager.
     * @return una lista de todos los establecimientos del manager.
     * @throws UserNotFoundException si no se encuentra el usuario.
     * @throws ManagerNotFoundException si no se encuentra el manager.
     * @throws StablishmentNotFoundException si no se encuentran establecimientos.
     */
    public List<stablishment> getAllStablishmentFromManager(String token) {
        String userId = jwtService.extractUserIDFromToken(token);
        user user = userService.getUser(userId);
        manager stabManager = managerService.getManager(user);

        List<stablishment> stablishments = stabRepo.findByManagerID(stabManager);
        if (stablishments.isEmpty()) {
            throw new StablishmentNotFoundException("No se han encontrado establecimientos asociados al manager con el ID " + userId);
        }
        return stablishments;
    }

    /**
     * Elimina un establecimiento.
     * @param stabID el ID del establecimiento.
     * @param token el token del manager.
     * @throws UserNotFoundException si no se encuentra el usuario.
     * @throws ManagerNotFoundException si no se encuentra el manager.
     * @throws StablishmentNotFoundException si no se encuentra el establecimiento.
     * @throws ManagerNotFromStablishmentException si el manager no pertenece al establecimiento.
     */
    public void deleteStab(Long stabID, String token) {
        String userId = jwtService.extractUserIDFromToken(token);
        user user = userService.getUser(userId);
        stablishment targetStab = getStab(stabID);

        if (userService.isManager(user)){
            manager stabManager = managerService.getManager(user);
            if (!managerService.isManagerInStab(targetStab, stabManager)) {
                throw new ManagerNotFromStablishmentException("El establecimiento con el ID " + stabID + " no pertenece al manager con el ID " + userId);
            }
        }
        stabRepo.deleteById(stabID);
    }

    /**
     * Añade un establecimiento.
     * @param newStab el nuevo establecimiento.
     * @throws UserNotFoundException si no se encuentra el usuario.
     * @throws ManagerNotFoundException si no se encuentra el manager.
     * @throws ManagerNotOwnerException si el manager no es el propietario.
     */
    public void addStablishment(stablishment newStab) {
        newStab.setManagerID(new ArrayList<>());
        stabRepo.save(newStab);
    }

    /**
     * Actualiza un establecimiento.
     * @param targetStab el establecimiento que se va a actualizar.
     * @param token el token del manager.
     * @throws StablishmentNotFoundException si no se encuentra el establecimiento.
     * @throws UserNotFoundException si no se encuentra el usuario.
     * @throws ManagerNotFoundException si no se encuentra el manager.
     * @throws ManagerNotFromStablishmentException si el manager no pertenece al establecimiento.
     */
    public void updateStab(stablishment targetStab, String token) {
        String userId = jwtService.extractUserIDFromToken(token);
        stablishment stablishment = getStab(targetStab.getStablishmentID());
        user user = userService.getUser(userId);

        if (userService.isManager(user)){
            manager stabManager = managerService.getManager(user);
            if (!managerService.isManagerInStab(targetStab, stabManager)) {
                throw new ManagerNotFromStablishmentException("El establecimiento con el ID " + stablishment.getStablishmentID() + " no pertenece al manager con el ID " + userId);
            }
        }

        stablishment.setCapacity(targetStab.getCapacity());
        stablishment.setStabAddress(targetStab.getStabAddress());
        stablishment.setCloseTime(targetStab.getCloseTime());
        stablishment.setOpenTime(targetStab.getOpenTime());
        stablishment.setStabName(targetStab.getStabName());
        stabRepo.save(stablishment);
    }

}
