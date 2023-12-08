package com.Clubbr.Clubbr.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "panicRepository")
@NoArgsConstructor
@AllArgsConstructor
public class panicAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long panicAlertId;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "eventName", referencedColumnName = "eventName"),
            @JoinColumn(name = "eventDate", referencedColumnName = "eventDate")
    })
    @JsonBackReference
    private event eventName;
    //@JsonBackReference(value = "eventPanicAlerts")

    @ManyToOne
    @JoinColumn(name = "stablishmentID", referencedColumnName = "stablishmentID")
    @JsonBackReference(value = "stablishmentPanicAlerts")
    private stablishment stablishmentID;
    //@JsonBackReference(value = "stablishmentPanicAlerts")

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    @JsonBackReference(value = "userPanicAlerts")
    private user userID;
    //@JsonBackReference(value = "userPanicAlerts")

    @Column(name = "panicAlertDateTime")
    private LocalDateTime panicAlertDateTime;


}