package com.Clubbr.Clubbr.Entity;

<<<<<<< HEAD
=======
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
>>>>>>> PruebaMerge06-12-2023
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

    @OneToMany(mappedBy = "stablishmentID")
    private List<worker> workers;

<<<<<<< HEAD
=======
    @ManyToMany
    @JoinTable(name = "stablishmentManager",
            joinColumns = @JoinColumn(name = "stablishmentID"),
            inverseJoinColumns = @JoinColumn(name = "managerID"))
    @JsonIgnore
    private List<manager> managerID;

>>>>>>> PruebaMerge06-12-2023
    @OneToMany(mappedBy = "stablishmentID", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "stablishmentInterestPoints")
    private List<interestPoint> interestPoints;

    @OneToMany(mappedBy = "stablishmentID", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "stablishmentEvents")
    private List<event> events;

<<<<<<< HEAD
    @Column(name = "floorPlan")
    private String floorPlan; //Plano de la planta del local

=======
    @OneToMany(mappedBy = "stablishmentID", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "stablishmentInventory")
    private List<item> inventory;
>>>>>>> PruebaMerge06-12-2023
}