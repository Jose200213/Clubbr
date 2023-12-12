package com.Clubbr.Clubbr.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import com.Clubbr.Clubbr.Entity.stablishment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "itemRepository")
@AllArgsConstructor
@NoArgsConstructor
public class item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemID")
    private Long itemID;

    @Column(name = "itemReference")
    private String itemReference;

    @Column(name = "itemName")
    private String itemName;

    @Column(name = "itemQuantity")
    private Long itemQuantity;

    @Column(name = "itemStock")
    private Long itemStock;

    @Column(name = "itemPrice")
    @JsonProperty("itemPrice")
    private float itemPrice;

    @Column(name = "itemDistributor")
    private String itemDistributor;

    @ManyToOne
    @JoinColumn(name = "stablishmentID")
    @JsonBackReference
    private stablishment stablishmentID;
}
