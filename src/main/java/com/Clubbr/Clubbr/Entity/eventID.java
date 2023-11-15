package com.Clubbr.Clubbr.Entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class eventID implements java.io.Serializable {

    private String eventName;
    private int stablishmentID;
    private LocalDate eventDate;
}
