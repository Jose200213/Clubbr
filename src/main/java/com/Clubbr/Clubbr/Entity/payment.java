package com.Clubbr.Clubbr.Entity;
<<<<<<< Updated upstream

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
=======
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

>>>>>>> Stashed changes

@Entity
@Getter
@Setter
<<<<<<< Updated upstream
@Table(name = "payment")
=======
@Table(name = "paymentRepository")
>>>>>>> Stashed changes
@NoArgsConstructor
@AllArgsConstructor
public class payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentID")
    private Long paymentID;

<<<<<<< Updated upstream
    @OneToOne
    @JoinColumn(name = "workerID", referencedColumnName = "workerID")
    @JsonBackReference
    private worker worker;

    @Column(name = "finalPayment")
    private float finalPayment;
=======
    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private user userID;

    @Column(name = "amount")
    private float amount;

    @Column(name = "paymentDate")
    private Date paymentDate;
>>>>>>> Stashed changes
}
