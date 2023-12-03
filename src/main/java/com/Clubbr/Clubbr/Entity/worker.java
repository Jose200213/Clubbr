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
    @OneToOne
    @JoinColumn (name = "userID")
    private user userID;

    @Id
    @ManyToOne
    @JoinColumn (name = "stablishmentID")
    @JsonBackReference(value = "stablishmentWorkers")
    private stablishment stablishmentID;

    @ManyToOne
    @JoinColumn (name = "interestPointID", referencedColumnName = "interestPointID")
    private interestPoint interestPointID;

}
