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
public class eventID implements java.io.Serializable {

    private String eventName;
    private Long stablishmentID;
    private LocalDate eventDate;
}
