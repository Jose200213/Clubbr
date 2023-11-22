package com.Clubbr.Clubbr.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


//TODO crear login y manejo de JWT

@Getter
@Setter
@Entity
@Table(name = "userRepository")
@NoArgsConstructor
@AllArgsConstructor
public class user {

    @Id
    @Column (name = "userID")
    private String userID;

    @Column (name = "password")
    private String password;

    @Column (name = "role")
    private int role;

    @Column (name = "name")
    private String name;

    @Column (name = "surname")
    private String surname;

    @Column (name = "country")
    private String country;

    @Column (name = "address")
    private String address;

    @Column (name = "email")
    private String email;
}


