package com.msiwy.GrandHotel.controller;

import com.msiwy.GrandHotel.dto.Response;
import com.msiwy.GrandHotel.entity.Booking;
import com.msiwy.GrandHotel.service.interfaces.IBookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class BookingControllerTest {

    @Mock
    private IBookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveBookingShouldReturnSuccessResponse() {
        // Arrange
        Long roomId = 1L;
        Long userId = 2L;
        Booking bookingRequest = new Booking();
        
        Response mockResponse = new Response();
        mockResponse.setStatusCode(HttpStatus.CREATED.value());
        mockResponse.setMessage("Booking created successfully");
        
        when(bookingService.saveBooking(anyLong(), anyLong(), any(Booking.class))).thenReturn(mockResponse);
        
        // Act
        ResponseEntity<Response> responseEntity = bookingController.saveBooking(roomId, userId, bookingRequest);
        
        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCodeValue());
        assertEquals("Booking created successfully", responseEntity.getBody().getMessage());
    }

    @Test
    void getAllBookingsShouldReturnBookingsList() {
        // Arrange
        Response mockResponse = new Response();
        mockResponse.setStatusCode(HttpStatus.OK.value());
        mockResponse.setMessage("All bookings retrieved");
        
        when(bookingService.getAllBookings()).thenReturn(mockResponse);
        
        // Act
        ResponseEntity<Response> responseEntity = bookingController.getAllBookings();
        
        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals("All bookings retrieved", responseEntity.getBody().getMessage());
    }

    @Test
    void getBookingByConfirmationCodeShouldReturnBooking() {
        // Arrange
        String confirmationCode = "ABC123";
        
        Response mockResponse = new Response();
        mockResponse.setStatusCode(HttpStatus.OK.value());
        mockResponse.setMessage("Booking found");
        
        when(bookingService.findBookingByConfirmationCode(anyString())).thenReturn(mockResponse);
        
        // Act
        ResponseEntity<Response> responseEntity = bookingController.getBookingByConfirmationCode(confirmationCode);
        
        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals("Booking found", responseEntity.getBody().getMessage());
    }

    @Test
    void cancelBookingShouldReturnSuccessResponse() {
        // Arrange
        Long bookingId = 1L;
        
        Response mockResponse = new Response();
        mockResponse.setStatusCode(HttpStatus.OK.value());
        mockResponse.setMessage("Booking cancelled successfully");
        
        when(bookingService.cancelBooking(anyLong())).thenReturn(mockResponse);
        
        // Act
        ResponseEntity<Response> responseEntity = bookingController.cancelBooking(bookingId);
        
        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals("Booking cancelled successfully", responseEntity.getBody().getMessage());
    }
}