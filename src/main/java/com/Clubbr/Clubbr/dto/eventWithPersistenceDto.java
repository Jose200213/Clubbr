package com.Clubbr.Clubbr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class eventWithPersistenceDto {
    private String eventName;
    private LocalDate eventDate;
    private LocalDate eventFinishDate;
    private String eventDescription;
    private String eventTime;

}
