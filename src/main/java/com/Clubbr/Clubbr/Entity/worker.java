package com.Clubbr.Clubbr.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "workerRepository", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userID", "stablishmentID", "eventName", "eventDate"})
})
@NoArgsConstructor
@AllArgsConstructor
public class worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn (name = "userID")
    @JsonProperty("userID")
    private user userID;

    @NotNull
    @ManyToOne
    @JoinColumn (name = "stablishmentID")
    @JsonBackReference(value = "stablishmentWorkers")
    private stablishment stablishmentID;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "eventName", referencedColumnName = "eventName"),
            @JoinColumn(name = "eventDate", referencedColumnName = "eventDate"),
    })
    @JsonBackReference(value = "eventWorkers")
    private event eventID;

    @ManyToOne
    @JoinColumn (name = "interestPointID", referencedColumnName = "interestPointID")
    private interestPoint interestPointID;

    @Column (name = "workingHours")
    private Long workingHours;

    @Column (name = "salary")
    @JsonProperty("salary")
    private float salary;

    @OneToMany(mappedBy = "workerID", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "workerPayments")
    private List<payment> paymentID;
    
    @Column(name = "attendance")
    private boolean attendance;

}
