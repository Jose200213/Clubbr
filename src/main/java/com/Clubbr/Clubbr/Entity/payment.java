package com.Clubbr.Clubbr.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "payment")
@NoArgsConstructor
@AllArgsConstructor
public class payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentID")
    private Long paymentID;

    @OneToOne
    @JoinColumn(name = "workerID", referencedColumnName = "workerID")
    @JsonBackReference
    private worker worker;

    @Column(name = "finalPayment")
    private float finalPayment;
}
