package com.Clubbr.Clubbr.Entity;

import com.Clubbr.Clubbr.utils.role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;



@Getter
@Setter
@Builder
@Entity
@Table(name = "userRepository")
@NoArgsConstructor
@AllArgsConstructor
public class user implements UserDetails {

    @Id
    @Column (name = "userID")
    @JsonProperty("userID")
    private String userID;

    @Column (name = "password")
    private String password;

    @Column (name = "userRole")
    @Enumerated(EnumType.STRING)
    private role userRole;

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

    @OneToMany(mappedBy = "userID", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ticket> tickets;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = userRole.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.name()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return userID;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


