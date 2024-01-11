/*package com.Clubbr.Clubbr;

import com.Clubbr.Clubbr.Entity.event;
import com.Clubbr.Clubbr.Service.eventService;
import com.Clubbr.Clubbr.Service.stablishmentService;
import com.Clubbr.Clubbr.Service.userService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.datatype.jsr310.*;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/*
@WebMvcTest  // Anotación para indicar que es un test para el controlador web
public class eventControllerTest {

    @MockBean
    private eventService eventService;

    @MockBean
    private stablishmentService stabService;

    @MockBean
    private userService userService;

    @Autowired  // Inyección de dependencias para MockMvc
    private MockMvc mvc;  // MockMvc nos permite hacer peticiones a los endpoints de nuestro controlador

    // ObjectMapper para convertir objetos en JSON y viceversa
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());  // Esto es para que sepa parsear LocalDate

    @Test  // Anotación para indicar que es un test
    public void saveEventOk() throws Exception {

        // Creación de un nuevo evento para la prueba
        var request = new event("testEvent", LocalDate.parse("2018-01-01"), null, LocalDate.parse("2021-05-03"), "testDescription", "22:00", 100, null, null);

        // Realización de la petición POST al endpoint "/stablishment/1/event/add"
        mvc.perform(MockMvcRequestBuilders
                        .post("/stablishment/1/event/add")  // Indicamos el método POST y el endpoint
                        .content(mapper.writeValueAsString(request))  // Convertimos el objeto evento en un string JSON
                        .contentType(MediaType.APPLICATION_JSON)  // Indicamos que el contenido de la petición es JSON
                        .accept(MediaType.APPLICATION_JSON))  // Indicamos que aceptamos una respuesta en formato JSON
                        .andExpect(status().isOk());  // Esperamos que el estado de la respuesta sea 200 (OK)
    }
}*/