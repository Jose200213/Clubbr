package com.Clubbr.Clubbr.Entity;

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
    @JoinColumn (name = "StablishmentID")
    private stablishment stablishmentID;

    @ManyToOne
    @JoinColumn (name = "InterestPoint")
    private interestPoint interestPoint;

}