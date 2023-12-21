package com.Clubbr.Clubbr.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Entity.stablishment;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class stablishmentService {

    @Autowired
    private stablishmentRepo stabRepo;

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
