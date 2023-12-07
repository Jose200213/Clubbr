package com.Clubbr.Clubbr.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stablishmentID")
    private Long stablishmentID;

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

    @OneToMany(mappedBy = "stablishmentID", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "stablishmentWorkers")
    private List<worker> workers;

    @ManyToMany
    @JoinTable(
            name = "manager_stablishmentRepository",
            joinColumns = @JoinColumn(name = "stablishmentID"),
            inverseJoinColumns = @JoinColumn(name = "managerID")
    )
    private List<manager> managerID;

    @OneToMany(mappedBy = "stablishmentID", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "stablishmentInterestPoints")
    private List<interestPoint> interestPoints;

    @OneToMany(mappedBy = "stablishmentID", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "stablishmentEvents")
    private List<event> events;

    @OneToMany(mappedBy = "stablishmentID", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<item> inventory;
}