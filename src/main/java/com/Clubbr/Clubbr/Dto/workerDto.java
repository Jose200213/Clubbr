package com.Clubbr.Clubbr.Dto;

import com.Clubbr.Clubbr.Entity.worker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class workerDto {
    private Long id;
    private String userID;
    private String userName;
    private String userSurname;
    private Long stablishmentID;
    private String eventID;
    private Long interestPointID;
    private Long workingHours;
    private float salary;
    private int attendance;

    public workerDto(worker worker) {
        this.id = worker.getId();
        this.userID = worker.getUserID().getUserID();
        this.userName = worker.getUserID().getName();
        this.userSurname = worker.getUserID().getSurname();
        this.stablishmentID = worker.getStablishmentID().getStablishmentID();
        if (worker.getEventID() == null) this.eventID = null;
        else this.eventID = worker.getEventID().getEventName();
        if (worker.getInterestPointID() == null) this.interestPointID = null;
        else this.interestPointID = worker.getInterestPointID().getInterestPointID();
        this.workingHours = worker.getWorkingHours();
        this.salary = worker.getSalary();
        this.attendance = worker.getAttendance().ordinal();
    }
}
