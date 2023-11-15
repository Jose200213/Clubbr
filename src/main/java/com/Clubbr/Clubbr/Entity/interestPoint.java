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
    @Column (name = "interestPointID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int interestPointID;


    @ManyToOne
    @JoinColumn (name = "stablishmentID")
    @JsonBackReference
    private stablishment stablishmentID;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "eventName", referencedColumnName = "eventName"),
            @JoinColumn(name = "eventDate", referencedColumnName = "eventDate")
    })
    private event eventName;

    @OneToMany(mappedBy = "interestPointID")
    private List<worker> workers;

    @Column (name = "xCoordinate")
    private float xCoordinate;

    @Column (name = "yCoordinate")
    private float yCoordinate;

    @Column (name = "description")
    private String description;

}
