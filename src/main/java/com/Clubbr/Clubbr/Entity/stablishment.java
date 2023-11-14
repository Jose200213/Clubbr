package com.Clubbr.Clubbr.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "stablishmentRepository")
@NoArgsConstructor
@AllArgsConstructor
public class stablishment {

    @Id
    @GeneratedValue
    @Column(name = "stablishmentID")
    private int stablishmentID;

    @Column(name = "stabName")
    private String stabName;

    @Column(name = "stabAddress")
    private String stabAddress;

    @Column(name = "openTime")
    private LocalTime openTime;

    @Column(name = "closeTime")
    private LocalTime closeTime;

    @Column(name = "capacity")
    private int capacity;

    @OneToMany(mappedBy = "stablishmentID")
    private List<worker> workers;

    @OneToMany(mappedBy = "stablishmentID")
    private List<interestPoint> interestPoints;

    @OneToMany(mappedBy = "stablishmentID")
    private List<event> events;
}