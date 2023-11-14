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
    //@JoinColumn(name = "eventID")
    private event eventID;

    @ManyToOne
    //@JoinColumn(name = "userID")
    private user userID;

    @ManyToOne
    //@JoinColumn(name = "stablishmentID")
    private stablishment stablishmentID;

    @Column(name = "ticketPrice")
    private float ticketPrice;

    @Column(name = "purchaseDateTime")
    private LocalDateTime purchaseDateTime;

    @Column(name = "validated")
    private boolean validated;

}
