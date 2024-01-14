package com.Clubbr.Clubbr.Dto;

import com.Clubbr.Clubbr.Entity.payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
