package com.Clubbr.Clubbr.Dto;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Entity.interestPoint;
import com.Clubbr.Clubbr.Entity.stablishment;
import com.Clubbr.Clubbr.Entity.worker;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class interestPointDto {
    private Long interestPointID;
    private Long stablishmentID;
    private String eventName;
    private float xCoordinate;
    private float yCoordinate;
    private String description;
    private List<workerDto> workers;

    public interestPointDto(interestPoint interestPoint) {
        this.interestPointID = interestPoint.getInterestPointID();
        this.stablishmentID = interestPoint.getStablishmentID().getStablishmentID();
        if (interestPoint.getEventName() == null) this.eventName = null;
        else this.eventName = interestPoint.getEventName().getEventName();
        this.xCoordinate = interestPoint.getXCoordinate();
        this.yCoordinate = interestPoint.getYCoordinate();
        this.description = interestPoint.getDescription();
        this.workers = new ArrayList<>();
        for (worker worker : interestPoint.getWorkers()) {
            this.workers.add(new workerDto(worker));
        }
    }
}
