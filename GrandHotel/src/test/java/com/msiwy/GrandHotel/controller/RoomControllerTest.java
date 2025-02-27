package com.msiwy.GrandHotel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msiwy.GrandHotel.dto.Response;
import com.msiwy.GrandHotel.service.interfaces.IRoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoomController.class) // Test skupiający się na kontrolerze RoomController.
@SpringJUnitConfig(RoomControllerTest.TestConfig.class) // Konfiguracja testowa ładowana eksplizytnie.
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IRoomService roomService; // Będzie zamockowane w konfiguracji.

    @Autowired
    private ObjectMapper objectMapper;

    private Response mockResponse;

    @BeforeEach
    void setUp() {
        mockResponse = new Response();
        mockResponse.setStatusCode(200);
        mockResponse.setMessage("Success");
    }

    @Test
    void testGetAllRooms() throws Exception {
        when(roomService.getAllRooms()).thenReturn(mockResponse); // Mockowanie metody serwisu.

        mockMvc.perform(get("/rooms/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    void testAddNewRoom() throws Exception {
        when(roomService.addNewRoom(any(), any(), any(), any())).thenReturn(mockResponse); // Mockowanie metody serwisu.

        mockMvc.perform(post("/rooms/add")
                        .param("roomType", "Deluxe")
                        .param("roomPrice", "150.00")
                        .param("roomDescription", "Luxury room")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"));
    }

    // Klasa konfiguracji testowej.
    @Configuration
    static class TestConfig {
        @Bean
        IRoomService roomService() {
            return mock(IRoomService.class); // Tworzenie bean-a mock dla serwisu IRoomService.
        }
    }
}