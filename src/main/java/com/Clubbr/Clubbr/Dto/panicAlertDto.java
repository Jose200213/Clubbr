package com.Clubbr.Clubbr.Dto;

import com.Clubbr.Clubbr.Entity.panicAlert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class panicAlertDto {

    private Long panicAlertId;
    private LocalDateTime panicAlertDateTime;
    private String userID;
    private String userName;
    private String userSurname;
    private Long stablishmentID;
    private String eventName;

    public panicAlertDto(panicAlert panicAlert) {
        this.panicAlertId = panicAlert.getPanicAlertId();
        this.panicAlertDateTime = panicAlert.getPanicAlertDateTime();
        this.userID = panicAlert.getUserID().getUserID();
        this.userName = panicAlert.getUserID().getName();
        this.userSurname = panicAlert.getUserID().getSurname();
        this.stablishmentID = panicAlert.getStablishmentID().getStablishmentID();
        this.eventName = panicAlert.getEventName().getEventName();
    }
}
