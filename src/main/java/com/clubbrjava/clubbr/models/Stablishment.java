package com.clubbrjava.clubbr.models;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.List;

import lombok.Getter;

@Entity
public class Stablishment {
    @Id @Getter
    private Long localID, capacity;

    @Getter
    private String address, info, name;

    @Getter
    private LocalTime openingTime, closeTime;

    //private int phoneNumber;

    private List<Item> inventory;
    private List<InterestPoint> interestPoints;

    public class Item{
        @Id @Getter
        private Long itemID;

        @Getter
        private String reference, name, distributor;

        @Getter
        private int quantity, stock;

        @Getter
        private float price;
    }

    public class InterestPoint {
        @Getter
        private String name;

        @Getter
        private int coordX, coordY;
        //private List<Worker> workers;
    }
}


