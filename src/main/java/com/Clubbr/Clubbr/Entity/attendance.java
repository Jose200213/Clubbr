package com.Clubbr.Clubbr.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "attendanceRepository")
@NoArgsConstructor
@AllArgsConstructor
public class attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendanceID")
    private Long attendanceID;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    @JsonBackReference(value = "userAttendance")
    private user userID;

    @Column(name = "attendance")
    private boolean attendance;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "eventName", referencedColumnName = "eventName"),
            @JoinColumn(name = "eventDate", referencedColumnName = "eventDate"),
    })
    @JsonBackReference
    private event eventName;

    @ManyToOne
    @JoinColumn(name = "stablishmentID", referencedColumnName = "stablishmentID")
    @JsonBackReference(value = "stablishmentAttendance")
    private stablishment stablishmentID;

}
