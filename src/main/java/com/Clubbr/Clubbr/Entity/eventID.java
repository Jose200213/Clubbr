package com.Clubbr.Clubbr.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class eventID implements java.io.Serializable {

    private String eventName;
    private int stablishmentID;
    private LocalDate eventDate;
}
