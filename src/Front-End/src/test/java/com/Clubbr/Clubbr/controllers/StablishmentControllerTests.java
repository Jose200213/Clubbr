package com.Clubbr.Clubbr.controllers;

import com.Clubbr.Clubbr.Entity.stablishment;
import org.junit.jupiter.api.Test;
import com.Clubbr.Clubbr.Controller.stablishmentController;
import com.Clubbr.Clubbr.Service.stablishmentService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalTime;

import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.Mockito.*;
/*
@ExtendWith(MockitoExtension.class)
public class StablishmentControllerTests {

    @InjectMocks
    private stablishmentController stablishmentController;

    @Mock
    private stablishmentService stablishmentService;

    @Test
    public void getStablishment(){
        stablishment stablishmentMock = new stablishment();
        stablishmentMock.setStabName("comedia");
        stablishmentMock.setCloseTime(LocalTime.of(12,49,30,0));
        stablishmentMock.setOpenTime(LocalTime.of(11, 43, 53, 0));
        stablishmentMock.setCapacity(240);
        stablishmentMock.setStabAddress("dfgdhgjdsas");
        when (stablishmentService.getStab(1L)).thenReturn(stablishmentMock);

        stablishment result = stablishmentController.getStab(1L);

        verify(stablishmentService, times(1)).getStab(1L);
    }

}*/
