package com.Clubbr.Clubbr.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "workerRepository")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(workerID.class)
public class worker {

    @Id
    @ManyToOne
    @JoinColumn (name = "userID")
    @JsonProperty("userID")
    private user userID;

    @Id
    @ManyToOne
    @JoinColumn (name = "stablishmentID")
    @JsonBackReference(value = "stablishmentWorkers")
    private stablishment stablishmentID;

    @Id
    @Column(name = "eventName")
    private String eventName;

    @Id
    @Column(name = "eventDate")
    private LocalDate eventDate;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "eventName", referencedColumnName = "eventName", insertable = false, updatable = false),
            @JoinColumn(name = "eventDate", referencedColumnName = "eventDate", insertable = false, updatable = false),
    })
    @JsonBackReference(value = "eventWorkers")
    private event event;

    @ManyToOne
    @JoinColumn (name = "interestPointID", referencedColumnName = "interestPointID")
    private interestPoint interestPointID;

    @Column (name = "workingHours")
    private Long workingHours;

    @Column (name = "salary")
    @JsonProperty("salary")
    private float salary;

    @Column(name = "attendance")
    private boolean attendance;

}
