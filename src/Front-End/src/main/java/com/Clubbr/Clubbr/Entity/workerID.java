package com.Clubbr.Clubbr.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class workerID implements java.io.Serializable {
    private String userID;
    private Long stablishmentID; //private stablishment stablishmentID; (No estoy seguro de cual es cual ni el porque)
    private String eventName;
    private LocalDate eventDate;
}
