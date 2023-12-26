package com.Clubbr.Clubbr.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import jakarta.persistence.*;

import java.time.YearMonth;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "paymentRepository")
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "paymentID")
public class payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentID")
    private Long paymentID;

    @ManyToOne
    @JoinColumn(name = "workerID")
    @JsonBackReference(value = "workerPayments")
    private worker workerID;        // Cambiado por user userID ?

    @ManyToOne
    @JoinColumn(name = "stablishmentID")
    private stablishment stablishmentID;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "eventName", referencedColumnName = "eventName"),
            @JoinColumn(name = "eventDate", referencedColumnName = "eventDate"),
    })
    private event eventID;              // No se por qué está, ya que cada worker tiene su evento si existe -> workerID.getEventID()

    @Column(name = "Paid")
    private boolean paid;

    @Column(name = "amount")
    private float amount;

    @Column(name = "paymentDate")
    private YearMonth paymentDate;

    // Puedes agregar más campos según tus necesidades

    // Constructores, getters y setters

}