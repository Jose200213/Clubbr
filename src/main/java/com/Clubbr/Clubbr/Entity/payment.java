package com.Clubbr.Clubbr.Entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "paymentRepository")
@NoArgsConstructor
@AllArgsConstructor
public class payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentID")
    private Long paymentID;

    @Id
    @ManyToOne
    @JoinColumn(name = "userID")
    private user userID;

    @Id
    @ManyToOne
    @JoinColumn(name = "stablishmentID")
    private stablishment stablishmentID;

    @Id
    @JoinColumn(name = "eventID") 
    private event eventID;

    @Column(name = "Paid")
    private Bool paid

    @Column(name = "amount")
    private float amount;

    @Column(name = "paymentDate")
    private Date paymentDate;

    // Puedes agregar más campos según tus necesidades

    // Constructores, getters y setters

}
