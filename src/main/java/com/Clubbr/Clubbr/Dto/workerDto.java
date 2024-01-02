package com.Clubbr.Clubbr.Dto;

import com.Clubbr.Clubbr.Entity.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class workerDto {
    private Long id;
    private String userID;
    private String userName;
    private Long stablishmentID;
    private String eventID;
    private Long interestPointID;
    private Long workingHours;
    private float salary;
    private boolean attendance;

    public workerDto(worker worker) {
        this.id = worker.getId();
        this.userID = worker.getUserID().getUserID();
        this.userName = worker.getUserID().getName();
        this.stablishmentID = worker.getStablishmentID().getStablishmentID();
        this.eventID = worker.getEventID().getEventName();
        this.interestPointID = worker.getInterestPointID().getInterestPointID();
        this.workingHours = worker.getWorkingHours();
        this.salary = worker.getSalary();
        this.attendance = worker.isAttendance();
    }
}
