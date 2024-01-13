package com.Clubbr.Clubbr.Entity;

import com.fasterxml.jackson.annotation.*;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "eventName")
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

    @Column(name = "eventPrice")
    @JsonProperty("eventPrice")
    private float eventPrice;

    @Column(name = "totalTickets")
    private int totalTickets;

    @Column(name = "eventBanner")
    private String eventBanner;

    @OneToMany(mappedBy = "eventName", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<interestPoint> interestPoints;

    @OneToMany(mappedBy = "eventID", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "eventWorkers")
    private List<worker> workers;

    @OneToMany(mappedBy = "eventName", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<panicAlert> panicAlerts;

    @OneToMany(mappedBy = "eventID", cascade = CascadeType.ALL)
    private List<payment> paymentID;

}
