package com.Clubbr.Clubbr.Entity;

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
    @Column (name = "interestPoint")
    @GeneratedValue
    private int interestPoint;


    @ManyToOne
    @JoinColumn (name = "stablishmentID")
    private stablishment stablishmentID;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "eventName", referencedColumnName = "eventName"),
            @JoinColumn(name = "eventDate", referencedColumnName = "eventDate")
    })
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
