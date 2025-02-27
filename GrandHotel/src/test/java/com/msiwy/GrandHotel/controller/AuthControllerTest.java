// package com.msiwy.GrandHotel.controller;

// import com.msiwy.GrandHotel.dto.LoginRequest;
// import com.msiwy.GrandHotel.dto.Response;
// import com.msiwy.GrandHotel.entity.User;
// import com.msiwy.GrandHotel.service.interfaces.IUserService;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;

// class AuthControllerTest {

//     @Mock
//     private IUserService userService;

//     @InjectMocks
//     private AuthController authController;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     void registerShouldReturnSuccessResponse() {
//         // Arrange
//         User user = new User();
//         user.setEmail("test@example.com");
//         user.setPassword("password123");
        
//         Response mockResponse = new Response();
//         mockResponse.setStatusCode(HttpStatus.CREATED.value());
//         mockResponse.setMessage("User registered successfully");
        
//         when(userService.register(any(User.class))).thenReturn(mockResponse);
        
//         // Act
//         ResponseEntity<Response> responseEntity = authController.register(user);
        
//         // Assert
//         assertNotNull(responseEntity);
//         assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCodeValue());
//         assertEquals("User registered successfully", responseEntity.getBody().getMessage());
//     }

//     @Test
//     void loginShouldReturnSuccessResponse() {
//         // Arrange
//         LoginRequest loginRequest = new LoginRequest();
//         loginRequest.setEmail("test@example.com");
//         loginRequest.setPassword("password123");
        
//         Response mockResponse = new Response();
//         mockResponse.setStatusCode(HttpStatus.OK.value());
//         mockResponse.setMessage("Login successful");
        
//         when(userService.login(any(LoginRequest.class))).thenReturn(mockResponse);
        
//         // Act
//         ResponseEntity<Response> responseEntity = authController.login(loginRequest);
        
//         // Assert
//         assertNotNull(responseEntity);
//         assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
//         assertEquals("Login successful", responseEntity.getBody().getMessage());
//         assertEquals("jwt-token-example", responseEntity.getBody().getData());
//     }
// }