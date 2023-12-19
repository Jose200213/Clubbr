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
    @JoinColumn(name = "userID")
    private user userID;

    @Column(name = "amount")
    private float amount;

    @Column(name = "paymentDate")
    private Date paymentDate;

    // Puedes agregar más campos según tus necesidades

    // Constructores, getters y setters

}
