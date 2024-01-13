package com.Clubbr.Clubbr.Dto;

import com.Clubbr.Clubbr.Entity.panicAlert;
import com.Clubbr.Clubbr.Entity.ticket;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.utils.role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class userDto {
    private String userID;
    private role userRole;
    private String name;
    private String surname;
    private String country;
    private String address;
    private String email;
    private Long telegramID;

    public userDto(user user) {
        this.userID = user.getUserID();
        this.userRole = user.getUserRole();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.country = user.getCountry();
        this.address = user.getAddress();
        this.email = user.getEmail();
        this.telegramID = user.getTelegramID();
    }
}
