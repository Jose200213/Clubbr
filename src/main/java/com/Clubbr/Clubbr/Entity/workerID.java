package com.Clubbr.Clubbr.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class workerID implements java.io.Serializable {
    private String userID;
    private stablishment stablishmentID;
}
