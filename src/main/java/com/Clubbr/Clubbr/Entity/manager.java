package com.Clubbr.Clubbr.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
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

    @Column(name = "isOwner")
    private boolean isOwner;

    @ManyToMany(mappedBy = "managerID", cascade = CascadeType.ALL)
    private List<stablishment> stablishmentID;
    
}
