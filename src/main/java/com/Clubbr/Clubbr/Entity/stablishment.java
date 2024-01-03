package com.Clubbr.Clubbr.Entity;

import com.fasterxml.jackson.annotation.*;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "stablishmentID")
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

    @Column(name = "floorPlan")
    private String floorPlan;

    @OneToMany(mappedBy = "stablishmentID", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "stablishmentWorkers")
    private List<worker> workers;

    @OneToMany(mappedBy = "stablishmentID", cascade = CascadeType.ALL)
    private List<payment> paymentID;

    @ManyToMany
    @JoinTable(name = "stablishmentManager",
            joinColumns = @JoinColumn(name = "stablishmentID"),
            inverseJoinColumns = @JoinColumn(name = "managerID"))
    @JsonIgnore
    private List<manager> managerID;

    @OneToMany(mappedBy = "stablishmentID", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "stablishmentInterestPoints")
    private List<interestPoint> interestPoints;

    @OneToMany(mappedBy = "stablishmentID", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "stablishmentEvents")
    private List<event> events;

    @OneToMany(mappedBy = "stablishmentID", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "stablishmentInventory")
    private List<item> inventory;

    @OneToMany(mappedBy = "stablishmentID")
    @JsonManagedReference(value = "stablishmentPanicAlerts")
    private List<panicAlert> panicAlerts;

    @OneToMany(mappedBy = "stablishmentID", cascade = CascadeType.ALL)
    private List<payment> payments;

}