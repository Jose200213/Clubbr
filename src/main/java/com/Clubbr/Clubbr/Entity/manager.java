package com.Clubbr.Clubbr.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "managerRepository")
@NoArgsConstructor
@AllArgsConstructor
public class manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "managerID")
    private Long managerID;

    @OneToOne
    @JoinColumn(name = "userID")
    private user userID;

    @OneToMany(mappedBy = "managerID", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "stablishmentManager")
    private List<stablishment> stablishmentID;
}