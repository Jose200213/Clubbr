package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Entity.item;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Service.itemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stablishment/{stablishmentID}")
public class itemController {

    @Autowired
    private itemService itemService;

    @PostMapping("/item/add")
    public void addItemToStablishment(@PathVariable("stablishmentID") Long stablishmentID, @RequestBody item newItem){
        itemService.addItemToStablishment(stablishmentID, newItem);
    }

    @GetMapping("item/all")
    public List<item> getItemsFromStablishment(@PathVariable("stablishmentID") Long stablishmentID){
        return itemService.getItemsFromStablishment(stablishmentID);
    }

    @GetMapping("/item/{itemID}")
    public item getItemFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("itemID") Long itemID){
        return itemService.getItemFromStablishment(stablishmentID, itemID);
    }

    @PutMapping("item/update/{itemID}")
    public void updateItemFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("itemID") Long itemID, @RequestBody item updateItem){
        itemService.updateItemFromStablishment(stablishmentID, itemID, updateItem);
    }

    @DeleteMapping("item/delete/{itemID}")
    public void deleteItemFromStablishment(@PathVariable("stablishmentID") Long stablishmentID, @PathVariable("itemID") Long itemID){
        itemService.deleteItemFromStablishment(stablishmentID, itemID);
    }
}
