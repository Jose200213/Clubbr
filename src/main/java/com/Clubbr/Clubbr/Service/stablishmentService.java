package com.Clubbr.Clubbr.Service;

<<<<<<< HEAD
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
=======
import java.util.ArrayList;
>>>>>>> PruebaMerge06-12-2023
import java.util.List;
import java.util.Objects;

<<<<<<< HEAD
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Entity.stablishment;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
=======
import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.advice.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import org.springframework.transaction.annotation.Transactional;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;
>>>>>>> PruebaMerge06-12-2023


@Service
public class stablishmentService {

    private final stablishmentRepo stabRepo;

    private final jwtService jwtService;

    private managerService managerService;

    private final userService userService;

<<<<<<< HEAD
    public List<stablishment> getAllStab() {
        //stablishment stablishment = new stablishment();
        //stablishment.getEvents().
        return stabRepo.findAll();
    }

    public stablishment getStab(Long stabID) { return stabRepo.findById(stabID).orElse(null);}

    public void deleteStab(Long stabID) { stabRepo.deleteById(stabID);}

    public void addStablishment(stablishment newStab) {stabRepo.save(newStab);}

    public void updateStab(Long stablishmentID, stablishment targetStab) {
        stablishment stablishment = stabRepo.findById(stablishmentID).orElse(null);
=======
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
        List<stablishment> stabList = stabRepo.findAll();
        if (stabList.isEmpty()) {
            throw new ResourceNotFoundException("Establecimiento");
        }
        return stabList;
    }

    /**
     * Obtiene un establecimiento por su ID.
     * @param stabID el ID del establecimiento.
     * @return el establecimiento con el ID proporcionado.
     * */
    public stablishment getStab(Long stabID) {
        return stabRepo.findById(stabID)
                .orElseThrow(() -> new ResourceNotFoundException("Establecimiento", "stablishmentID", stabID));
    }

    /**
     * Obtiene todos los establecimientos de un manager.
     * @param token el token del manager.
     * @return una lista de todos los establecimientos del manager.
     */
    public List<stablishment> getAllStablishmentFromManager(String token) {
        String userId = jwtService.extractUserIDFromToken(token);
        user user = userService.getUser(userId);
        manager stabManager = managerService.getManager(user);

        List<stablishment> stablishments = stabRepo.findByManagerID(stabManager);
        if (stablishments.isEmpty()) {
            throw new ResourceNotFoundException("Establecimiento");
        }
        return stablishments;
    }

    /**
     * Elimina un establecimiento.
     * @param stabID el ID del establecimiento.
     * @param token el token del manager.
     */
    public void deleteStab(Long stabID, String token) {
        String userId = jwtService.extractUserIDFromToken(token);
        user user = userService.getUser(userId);
        stablishment targetStab = getStab(stabID);

        if (userService.isManager(user)){
            manager stabManager = managerService.getManager(user);
            if (!managerService.isManagerInStab(targetStab, stabManager)) {
                throw new ResourceNotFoundException("Manager", "userID", userId, "Establecimiento", "stablishmentID", targetStab.getStablishmentID());
            }
        }
        stabRepo.deleteById(stabID);
    }

    /**
     * Añade un establecimiento.
     */
    public void addStablishment(stablishment newStab) {
        newStab.setManagerID(new ArrayList<>());
        stabRepo.save(newStab);
    }

    /**
     * Actualiza un establecimiento.
     * @param targetStab el establecimiento que se va a actualizar.
     * @param token el token del manager.
     */
    public void updateStab(stablishment targetStab, String token) {
        String userId = jwtService.extractUserIDFromToken(token);
        stablishment stablishment = getStab(targetStab.getStablishmentID());
        user user = userService.getUser(userId);

        if (userService.isManager(user)){
            manager stabManager = managerService.getManager(user);
            if (!managerService.isManagerInStab(targetStab, stabManager)) {
                throw new ResourceNotFoundException("Manager", "userID", userId, "Establecimiento", "stablishmentID", targetStab.getStablishmentID());
            }
        }
>>>>>>> PruebaMerge06-12-2023

        stablishment.setCapacity(targetStab.getCapacity());
        stablishment.setStabAddress(targetStab.getStabAddress());
        stablishment.setCloseTime(targetStab.getCloseTime());
        stablishment.setOpenTime(targetStab.getOpenTime());
        stablishment.setStabName(targetStab.getStabName());
        stabRepo.save(targetStab);
    }

    public void uploadFloorPlan(Long stablishmentID, MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        Path path = Paths.get("user/image/" + fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        stablishment stablishment = stabRepo.findById(stablishmentID).orElse(null);
        stablishment.setFloorPlan(path.toString());
        stabRepo.save(stablishment);
    }

}
