package com.Clubbr.Clubbr.Dto;

import com.Clubbr.Clubbr.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class stablishmentListDto {
    private Long stablishmentID;
    private String stabName;
    private String stabAddress;
    private String openTime;
    private String closeTime;
    private int capacity;

    public stablishmentListDto(stablishment stablishment) {
        this.stablishmentID = stablishment.getStablishmentID();
        this.stabName = stablishment.getStabName();
        this.stabAddress = stablishment.getStabAddress();
        this.openTime = stablishment.getOpenTime().toString();
        this.closeTime = stablishment.getCloseTime().toString();
        this.capacity = stablishment.getCapacity();
    }
}
