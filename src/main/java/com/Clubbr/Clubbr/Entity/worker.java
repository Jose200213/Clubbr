package com.Clubbr.Clubbr.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
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
    @JoinColumn (name = "UserID")
    private user userID;

    @Id
    @ManyToOne
    @JoinColumn (name = "stablishmentID")
    @JsonBackReference(value = "stablishmentWorkers")
    private stablishment stablishmentID;

    @ManyToOne
    @JoinColumn (name = "InterestPointID", referencedColumnName = "InterestPointID")
    private interestPoint interestPointID;

    @Column(name = "Attendance")
    private boolean attendance;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "eventName", referencedColumnName = "eventName"),
            @JoinColumn(name = "eventDate", referencedColumnName = "eventDate"),
    })
    @JsonBackReference
    private event eventName;


}
