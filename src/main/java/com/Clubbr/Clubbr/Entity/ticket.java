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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticketID")
    private int ticketID;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "eventName", referencedColumnName = "eventName"),
            @JoinColumn(name = "eventDate", referencedColumnName = "eventDate")
    })
    private event eventName;

    @ManyToOne
    @JoinColumn(name = "userID")
    private user userID;

    @ManyToOne
    @JoinColumn(name = "stablishmentID")
    private stablishment stablishmentID;

    @Column(name = "ticketPrice")
    private float ticketPrice;

    @Column(name = "purchaseDateTime")
    private LocalDateTime purchaseDateTime;

    @Column(name = "validated")
    private boolean validated;

}
