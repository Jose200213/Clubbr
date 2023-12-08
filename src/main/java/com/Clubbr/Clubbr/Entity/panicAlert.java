package com.Clubbr.Clubbr.Entity;



import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "panicRepository")
@NoArgsConstructor
@AllArgsConstructor
public class panicAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long panicAlertId;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "eventName", referencedColumnName = "eventName"),
            @JoinColumn(name = "eventDate", referencedColumnName = "eventDate"),
            @JoinColumn(name = "stablishmentID", referencedColumnName = "stablishmentID"),

    })
    @JsonBackReference(value = "eventPanicAlert")
    private event eventName;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    @JsonBackReference(value = "userPanicAlert")
    private user userID;

    @Column(name = "panicAlertDate")
    private LocalDateTime panicAlertDateTime;


}