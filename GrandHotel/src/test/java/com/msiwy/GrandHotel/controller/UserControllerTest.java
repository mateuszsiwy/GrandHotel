package com.msiwy.GrandHotel.controller;

import com.msiwy.GrandHotel.dto.Response;
import com.msiwy.GrandHotel.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsersShouldReturnUsersList() {
        // Arrange
        Response mockResponse = new Response();
        mockResponse.setStatusCode(HttpStatus.OK.value());
        mockResponse.setMessage("All users retrieved");
        
        when(userService.getAllUsers()).thenReturn(mockResponse);
        
        // Act
        ResponseEntity<Response> responseEntity = userController.getAllUsers();
        
        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals("All users retrieved", responseEntity.getBody().getMessage());
    }

    @Test
    void getUserByIdShouldReturnUser() {
        // Arrange
        String userId = "123";
        
        Response mockResponse = new Response();
        mockResponse.setStatusCode(HttpStatus.OK.value());
        mockResponse.setMessage("User found");
        
        when(userService.getUserById(anyString())).thenReturn(mockResponse);
        
        // Act
        ResponseEntity<Response> responseEntity = userController.getAllUsers(userId);
        
        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals("User found", responseEntity.getBody().getMessage());
    }

    @Test
    void deleteUserShouldReturnSuccessResponse() {
        // Arrange
        String userId = "123";
        
        Response mockResponse = new Response();
        mockResponse.setStatusCode(HttpStatus.OK.value());
        mockResponse.setMessage("User deleted successfully");
        
        when(userService.deleteUser(anyString())).thenReturn(mockResponse);
        
        // Act
        ResponseEntity<Response> responseEntity = userController.deleteUser(userId);
        
        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals("User deleted successfully", responseEntity.getBody().getMessage());
    }

    @Test
    void getLoggedInUserProfileShouldReturnUserInfo() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        
        when(authentication.getName()).thenReturn("test@example.com");
        
        Response mockResponse = new Response();
        mockResponse.setStatusCode(HttpStatus.OK.value());
        mockResponse.setMessage("User profile retrieved");
        
        when(userService.getMyInfo(anyString())).thenReturn(mockResponse);
        
        // Act
        ResponseEntity<Response> responseEntity = userController.getLoggedInUserProfile();
        
        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals("User profile retrieved", responseEntity.getBody().getMessage());
    }
}