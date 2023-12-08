package com.Clubbr.Clubbr.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "eventRepository")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(eventID.class)
public class event {

    @Id
    @Column(name = "eventName")
    private String eventName;

    @Id
    @Column(name = "eventDate")
    private LocalDate eventDate;

    @Id
    @ManyToOne
    @JoinColumn (name = "stablishmentID")
    @JsonBackReference(value = "stablishmentEvents")
    private stablishment stablishmentID;

    @Column (name = "eventFinishDate")
    private LocalDate eventFinishDate;

    @Column(name = "eventDescription")
    private String eventDescription;

    @Column(name = "eventTime")
    private String eventTime;

    @Column(name = "totalTickets")
    private int totalTickets;

    @OneToMany(mappedBy = "eventName", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<interestPoint> interestPoints;

    @OneToMany(mappedBy = "eventName", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "eventWorkers")
    private List<worker> workers;

    @OneToMany(mappedBy = "eventName", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<panicAlert> panicAlerts;

    @OneToMany(mappedBy = "eventName", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<attendance> attendances;

}
