package com.Clubbr.Clubbr.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.Clubbr.Clubbr.Entity.ticket;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ticketDto {
    private Long ticketID;
    private String eventName;
    private LocalDate eventDate;
    private String eventTime;
    private String stabName;
    private float ticketPrice;
    private LocalDateTime purchaseDateTime;
    private String ticketBanner;

    public ticketDto(ticket ticket) {
        this.ticketID = ticket.getTicketID();
        this.eventName = ticket.getEventName().getEventName();
        this.eventDate = ticket.getEventName().getEventDate();
        this.ticketPrice = ticket.getTicketPrice();
        this.purchaseDateTime = ticket.getPurchaseDateTime();
        this.ticketBanner = ticket.getEventName().getEventBanner();
        this.eventTime = ticket.getEventName().getEventTime();
        this.stabName = ticket.getStablishmentID().getStabName();

    }

}
