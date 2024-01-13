package com.Clubbr.Clubbr.Dto;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.payment;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.worker;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class paymentDto {
    private Long paymentID;
    private Long workerID;
    private Long stablishmentID;
    private String eventName;
    private boolean paid;
    private float amount;
    private String paymentDate;

    public paymentDto(payment payment) {
        this.paymentID = payment.getPaymentID();
        this.workerID = payment.getWorkerID().getId();
        this.stablishmentID = payment.getStablishmentID().getStablishmentID();
        this.eventName = payment.getEventID().getEventName();
        this.paid = payment.isPaid();
        this.amount = payment.getAmount();
        this.paymentDate = payment.getPaymentDate().toString();
    }

}
