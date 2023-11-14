package com.Clubbr.Clubbr.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "ticketRepository")
@NoArgsConstructor
@AllArgsConstructor
public class ticket {

    @Id
    @GeneratedValue
    private int ticketID;

    @ManyToOne
    private event eventID;

    private user userID;

    private stablishment stablishmentID;

    private float ticketPrice;

    private LocalDateTime purchaseDateTime;



}
