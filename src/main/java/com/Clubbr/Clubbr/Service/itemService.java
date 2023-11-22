package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Entity.item;
import com.Clubbr.Clubbr.Entity.stablishment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import com.Clubbr.Clubbr.Repository.itemRepo;

import java.util.List;

@Service
public class itemService {

    @Autowired
    private stablishmentRepo stabRepo;

    @Autowired
    private itemRepo itemRepo;

    @Transactional
    public void addItemToStablishment(Long stablishmentID, item newItem){
        stablishment stablishment = stabRepo.findById(stablishmentID).orElse(null);

        newItem.setItemQuantity(newItem.getItemStock());
        newItem.setStablishmentID(stablishment);
        stablishment.getInventory().add(newItem);
        stabRepo.save(stablishment);
        itemRepo.save(newItem);
    }

    @Transactional(readOnly = true)
    public List<item> getItemsFromStablishment(Long stablishmentID){
        stablishment stablishment = stabRepo.findById(stablishmentID).orElse(null);
        return itemRepo.findByStablishmentID(stablishment);
    }

    @Transactional(readOnly = true)
    public item getItemFromStablishment(Long stablishmentID, Long itemID){
        item item = itemRepo.findById(itemID).orElse(null);
        stablishment stablishment = stabRepo.findById(stablishmentID).orElse(null);

        if (item.getStablishmentID() == stablishment){
            return item;
        }
        return null;
    }

    @Transactional
    public void updateItemFromStablishment(Long stablishmentID, Long itemID, item updateItem){
        stablishment stablishment = stabRepo.findById(stablishmentID).orElse(null);
        item item = itemRepo.findById(itemID).orElse(null);
        if (item.getStablishmentID() == stablishment){
            item.setItemQuantity(updateItem.getItemQuantity());
            item.setItemDistributor(updateItem.getItemDistributor());
            item.setItemName(updateItem.getItemName());
            item.setItemPrice(updateItem.getItemPrice());
            item.setItemReference(updateItem.getItemReference());
            item.setItemStock(updateItem.getItemStock());

            itemRepo.save(item);
        }
    }

    @Transactional
    public void deleteItemFromStablishment(Long stablishmentID, Long itemID){
        stablishment stablishment = stabRepo.findById(stablishmentID).orElse(null);
        item item = itemRepo.findById(itemID).orElse(null);

        if (item.getStablishmentID() == stablishment){
            itemRepo.delete(item);
        }
    }
}
