package be.intecbrussel.ProductWebShop.Controller;

import be.intecbrussel.ProductWebShop.controller.AuthController;
import be.intecbrussel.ProductWebShop.dto.AuthDto.LoginRequest;
import be.intecbrussel.ProductWebShop.dto.AuthDto.LoginResponse;
import be.intecbrussel.ProductWebShop.model.User;
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

    @Test
    void testSaveUser_Success() {
        // Arrange
        User user = createSampleUser();
        when(registerService.createUser(user)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<User> result = authController.saveUser(user);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(user, result.getBody());
    }

    @Test
    void testSaveUser_Failure() {
        // Arrange
        User user = createSampleUser();
        when(registerService.createUser(user)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> result = authController.saveUser(user);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testLoginUser_Success() {
        // Arrange
        LoginRequest loginRequest = createSampleLoginRequest();
        User user = createSampleUser();
        when(userService.getUser(loginRequest.getEmail())).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<LoginResponse> result = authController.loginUser(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(user.getId(), result.getBody().getId());
        assertEquals(user.getEmail(), result.getBody().getEmail());
        assertEquals(user.getPassword(), result.getBody().getPassword());
    }

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

    private User createSampleUser() {
        User user = new User("ee", "ee");
        return user;
    }

    private LoginRequest createSampleLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("ee", "ee");
        return loginRequest;
    }
}
