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

    @ManyToOne
    @JoinColumn(name = "workerID")
    private worker workerID;        // Cambiado por user userID ?

    @ManyToOne
    @JoinColumn(name = "stablishmentID")
    private stablishment stablishmentID;

    @ManyToOne
    @JoinColumn(name = "eventID")
    private event eventID;              // No se por qué está, ya que cada worker tiene su evento si existe -> workerID.getEventID()

    @Column(name = "Paid")
    private boolean paid;

    @Column(name = "amount")
    private float amount;

    @Column(name = "paymentDate")
    private Date paymentDate;

    // Puedes agregar más campos según tus necesidades

    // Constructores, getters y setters

}