package com.Clubbr.Clubbr.Entity;

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
    private String eventDate;

    @Id
    @ManyToOne
    @JoinColumn (name = "stablishmentID")
    private stablishment stablishmentID;

    @Column (name = "eventFinishDate")
    private LocalDate eventFinishDate;

    @Column(name = "eventDescription")
    private String eventDescription;

    @Column(name = "eventTime")
    private String eventTime;

    @OneToMany(mappedBy = "eventName")
    private List<interestPoint> interestPoint;

}
