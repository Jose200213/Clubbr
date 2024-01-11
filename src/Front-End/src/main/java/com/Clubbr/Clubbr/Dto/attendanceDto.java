package com.Clubbr.Clubbr.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class attendanceDto {

    private String userID;
    private String name;
    private String surname;
    private String stabName;
    private String eventName;
    private LocalDate eventDate;
    private Boolean attendance;

}
