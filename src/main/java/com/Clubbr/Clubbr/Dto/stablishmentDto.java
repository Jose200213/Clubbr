package com.Clubbr.Clubbr.Dto;

import com.Clubbr.Clubbr.Entity.stablishment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class stablishmentDto {
    private Long stablishmentID;
    private String stabName;
    private String stabAddress;
    private LocalTime openTime;
    private LocalTime closeTime;
    private int capacity;
    private String banner;
    private String wideBanner;


    public stablishmentDto(stablishment stablishment) {
        this.stablishmentID = stablishment.getStablishmentID();
        this.stabName = stablishment.getStabName();
        this.stabAddress = stablishment.getStabAddress();
        this.openTime = stablishment.getOpenTime();
        this.closeTime = stablishment.getCloseTime();
        this.capacity = stablishment.getCapacity();
        this.banner = stablishment.getBanner();
        if (stablishment.getWideBanner() == null) this.wideBanner = null;
        else this.wideBanner = stablishment.getWideBanner();
    }
}
