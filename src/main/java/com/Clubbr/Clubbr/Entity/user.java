package com.Clubbr.Clubbr.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


//TODO crear login y manejo de JWT

@Getter
@Setter
@Entity
@Table(name = "userRepository")
@NoArgsConstructor
@AllArgsConstructor
public class user {

    @Id
    @Column (name = "UserID")
    private String userID;

    @Column (name = "Password")
    private String password;

    @Column (name = "Role")
    private int role;

    @Column (name = "Name")
    private String name;

    @Column (name = "Surname")
    private String surname;

    @Column (name = "Country")
    private String country;

    @Column (name = "Address")
    private String address;

    @Column (name = "Email")
    private String email;

    @OneToMany(mappedBy = "userID", cascade = CascadeType.ALL)
    @JsonBackReference(value = "userPanicAlerts")
    private List<panicAlert> panicAlertList;
}


