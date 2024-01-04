package com.Clubbr.Clubbr.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.Clubbr.Clubbr.Dto.stablishmentDto;
import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.advice.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


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
        List<stablishment> stabList = stabRepo.findAll();
        if (stabList.isEmpty()) {
            throw new ResourceNotFoundException("Establecimiento");
        }
        return stabList;
    }

    @Transactional(readOnly = true)
    public List<stablishmentDto> getAllStabDto() {
        List<stablishment> stabList = getAllStab();
        return stabList.stream().map(stablishmentDto::new).collect(Collectors.toList());
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

    public stablishmentDto getStabDto(Long stabID) {
        stablishment stab = getStab(stabID);
        return new stablishmentDto(stab);
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
    public void deleteStab(Long stabID, String token) throws IOException {
        managerService.checkManagerIsFromStab(stabID, token);

        String floorPlanPath = getStab(stabID).getFloorPlan();
        Path path = Paths.get(floorPlanPath);
        Files.deleteIfExists(path);

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
        managerService.checkManagerIsFromStab(targetStab.getStablishmentID(), token);

        stablishment stablishment = getStab(targetStab.getStablishmentID());

        stablishment.setCapacity(targetStab.getCapacity());
        stablishment.setStabAddress(targetStab.getStabAddress());
        stablishment.setCloseTime(targetStab.getCloseTime());
        stablishment.setOpenTime(targetStab.getOpenTime());
        stablishment.setStabName(targetStab.getStabName());
        stabRepo.save(stablishment);
    }

    public void uploadFloorPlan(Long stablishmentID, MultipartFile file, String token) throws IOException {
        managerService.checkManagerIsFromStab(stablishmentID, token);

        stablishment targetStab = getStab(stablishmentID);

        String fileExtension = StringUtils.getFilenameExtension(Objects.requireNonNull(file.getOriginalFilename()));
        String newFileName = "stablishment_" + stablishmentID + "." + fileExtension;

        Path path = Paths.get("user/image/" + newFileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        targetStab.setFloorPlan(path.toString());
        stabRepo.save(targetStab);
    }

    public String getFloorPlan(Long stablishmentID, String token) throws Exception {
        managerService.checkManagerIsFromStab(stablishmentID, token);

        stablishment targetStab = getStab(stablishmentID);

        String floorPlanPath = targetStab.getFloorPlan().replace("\\", "/");
        Path path = Paths.get(floorPlanPath);

        if (Files.exists(path) && Files.isReadable(path)) {
            String serverAddress = "http://localhost:8080/";
            String imageUrl = serverAddress + floorPlanPath;
            return imageUrl;
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    }

}

