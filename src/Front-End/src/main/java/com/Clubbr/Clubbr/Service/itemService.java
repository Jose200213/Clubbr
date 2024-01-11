package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.item;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.advice.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Repository.itemRepo;
import com.Clubbr.Clubbr.Entity.manager;

import java.util.List;

@Service
public class itemService {

    @Autowired
    private stablishmentService stabService;

    @Autowired
    private stablishmentRepo stabRepo;

    @Autowired
    private itemRepo itemRepo;

    @Autowired
    private userService userService;

    @Autowired
    private jwtService jwtService;

    @Autowired
    private managerService managerService;

    @Transactional
    public void addItemToStablishment(Long stablishmentID, item newItem, String token){
        stablishment targetStab = stabService.getStab(stablishmentID);
        user user = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (userService.isManager(user)){
            manager targetManager = managerService.getManager(userService.getUser(jwtService.extractUserIDFromToken(token)));
            if (!managerService.isManagerInStab(targetStab, targetManager)){
                throw new ResourceNotFoundException("Manager", "userID", user.getUserID(), "Establecimiento", "stablishmentID", targetStab.getStablishmentID());
            }
        }

        newItem.setItemQuantity(newItem.getItemStock());
        newItem.setStablishmentID(targetStab);
        targetStab.getInventory().add(newItem);
        stabRepo.save(targetStab);
        itemRepo.save(newItem);
    }

    @Transactional(readOnly = true)
    public List<item> getItemsFromStablishment(Long stablishmentID, String token){
        stablishment targetStab = stabService.getStab(stablishmentID);
        user user = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (userService.isManager(user)){
            manager targetManager = managerService.getManager(userService.getUser(jwtService.extractUserIDFromToken(token)));
            if (!managerService.isManagerInStab(targetStab, targetManager)){
                throw new ResourceNotFoundException("Manager", "userID", user.getUserID(), "Establecimiento", "stablishmentID", targetStab.getStablishmentID());
            }
        }
        return itemRepo.findByStablishmentID(targetStab);
    }

    @Transactional(readOnly = true)
    public item getItemFromStablishment(Long stablishmentID, Long itemID, String token){
        stablishment targetStab = stabService.getStab(stablishmentID);
        user user = userService.getUser(jwtService.extractUserIDFromToken(token));

        if (userService.isManager(user)){
            manager targetManager = managerService.getManager(userService.getUser(jwtService.extractUserIDFromToken(token)));
            if (!managerService.isManagerInStab(targetStab, targetManager)){
                throw new ResourceNotFoundException("Manager", "userID", user.getUserID(), "Establecimiento", "stablishmentID", targetStab.getStablishmentID());
            }
        }

        item item = getItem(itemID);
        if (item.getStablishmentID() != targetStab){
            throw new ResourceNotFoundException("Item", "itemID", itemID, "Establecimiento", "stablishmentID", targetStab.getStablishmentID());
        }
        
        return item;
    }

    public item getItem(Long itemID){
        return itemRepo.findById(itemID).orElseThrow(() -> new ResourceNotFoundException("Item", "itemID", itemID));
    }

    @Transactional
    public void updateItemFromStablishment(Long stablishmentID, Long itemID, item updateItem, String token){
        item item = getItemFromStablishment(stablishmentID, itemID, token);

        item.setItemQuantity(updateItem.getItemQuantity());
        item.setItemDistributor(updateItem.getItemDistributor());
        item.setItemName(updateItem.getItemName());
        item.setItemPrice(updateItem.getItemPrice());
        item.setItemReference(updateItem.getItemReference());
        item.setItemStock(updateItem.getItemStock());

        itemRepo.save(item);
    }

    @Transactional
    public void deleteItemFromStablishment(Long stablishmentID, Long itemID, String token){
        item item = getItemFromStablishment(stablishmentID, itemID, token);
        itemRepo.delete(item);
    }
}
