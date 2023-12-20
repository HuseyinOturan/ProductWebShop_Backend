package be.intecbrussel.ProductWebShop.Controller;

import be.intecbrussel.ProductWebShop.controller.AuthController;
import be.intecbrussel.ProductWebShop.dto.AuthDto.LoginRequest;
import be.intecbrussel.ProductWebShop.dto.AuthDto.LoginResponse;
import be.intecbrussel.ProductWebShop.dto.AuthDto.RegisterRequest;
import be.intecbrussel.ProductWebShop.model.AuthUser;
import be.intecbrussel.ProductWebShop.service.RegisterService;
import be.intecbrussel.ProductWebShop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @Mock
    private RegisterService registerService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // de test is geslagd zonder de security.

//    @Test
//    void testSaveUser_Success() {
//        // Arrange
//        AuthUser authUser = createSampleUser();
//        when(registerService.createUser(new RegisterRequest(authUser.getEmail(), authUser.getPassword()))).thenReturn(Optional.of(authUser));
//
//        // Act
//        ResponseEntity<AuthUser> result = authController.saveUser(new RegisterRequest(authUser.getEmail(), authUser.getPassword()));
//
//        // Assert
//        //      assertEquals(HttpStatus.OK, result.getStatusCode());
//        assertEquals(authUser, result.getBody());
//    }

    @Test
    void testSaveUser_Failure() {
        // Arrange
        AuthUser authUser = createSampleUser();
        when(registerService.createUser(new RegisterRequest(authUser.getEmail(), authUser.getPassword()))).thenReturn(Optional.empty());

        // Act
        ResponseEntity<AuthUser> result = authController.saveUser(new RegisterRequest(authUser.getEmail(), authUser.getEmail()));

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }
    // de test is geslagd zonder de security.
//    @Test
//    void testLoginUser_Success() {
//        // Arrange
//        LoginRequest loginRequest = createSampleLoginRequest();
//        AuthUser authUser = createSampleUser();
//        when(userService.getUser(loginRequest.getEmail())).thenReturn(Optional.of(authUser));
//
//        // Act
//        ResponseEntity<LoginResponse> result = authController.loginUser(loginRequest);
//
//        // Assert
//        //   assertEquals(HttpStatus.OK, result.getStatusCode());
//        assertNotNull(result.getBody());
//
//        assertEquals(authUser.getEmail(), result.getBody().getEmail());
//
//    }

    @Test
    void testLoginUser_UserNotFound() {
        // Arrange
        LoginRequest loginRequest = createSampleLoginRequest();
        when(userService.getUser(loginRequest.getEmail())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<LoginResponse> result = authController.loginUser(loginRequest);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    private AuthUser createSampleUser() {
        AuthUser authUser = new AuthUser("ee", "ee");
        return authUser;
    }

    private LoginRequest createSampleLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("ee", "ee");
        return loginRequest;
    }
}
