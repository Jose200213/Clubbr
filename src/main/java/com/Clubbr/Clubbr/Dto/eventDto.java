package com.Clubbr.Clubbr.Dto;

import com.Clubbr.Clubbr.Entity.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class eventDto {
    private String eventName;
    private String eventDate;
    private Long stablishmentID;
    private String eventDescription;
    private float eventPrice;
    private int totalTickets;

    public eventDto(event event) {
        this.eventName = event.getEventName();
        this.eventDate = event.getEventDate().toString();
        this.stablishmentID = event.getStablishmentID().getStablishmentID();
        this.eventDescription = event.getEventDescription();
        this.eventPrice = event.getEventPrice();
        this.totalTickets = event.getTotalTickets();
    }
}
