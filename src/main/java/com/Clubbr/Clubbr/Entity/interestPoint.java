package com.Clubbr.Clubbr.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "interestPointRepository")
@NoArgsConstructor
@AllArgsConstructor
public class interestPoint {

    @Id
    @Column (name = "InterestPoint")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int interestPoint;


    @ManyToOne
    @JoinColumn (name = "stablishmentID")
    @JsonBackReference(value = "stablishmentInterestPoints")
    private stablishment stablishmentID;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "eventName", referencedColumnName = "eventName"),
            @JoinColumn(name = "eventDate", referencedColumnName = "eventDate")
    })
    @JsonBackReference
    private event eventName;

    @OneToMany(mappedBy = "interestPoint")
    private List<worker> workers;

    @Column (name = "xCoordinate")
    private float xCoordinate;

    @Column (name = "yCoordinate")
    private float yCoordinate;

    @Column (name = "description")
    private String description;

}
