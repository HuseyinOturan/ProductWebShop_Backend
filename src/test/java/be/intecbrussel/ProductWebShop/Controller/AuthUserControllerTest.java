package be.intecbrussel.ProductWebShop.Controller;

import be.intecbrussel.ProductWebShop.controller.UserController;
import be.intecbrussel.ProductWebShop.model.AuthUser;
import be.intecbrussel.ProductWebShop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class AuthUserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetUserByEmail_Success() {
        // Arrange
        String userEmail = "test@example.com";
        AuthUser expectedAuthUser = createSampleUser();
        when(userService.getUser(userEmail)).thenReturn(Optional.of(expectedAuthUser));

        // Act
        ResponseEntity<AuthUser> result = userController.getUserByEmail(userEmail);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedAuthUser, result.getBody());
    }

    @Test
    void testGetUserByEmail_NotFound() {
        // Arrange
        String userEmail = "nonexistent@example.com";
        when(userService.getUser(userEmail)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<AuthUser> result = userController.getUserByEmail(userEmail);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testGetAllUser_Success() {
        // Arrange
        List<AuthUser> authUserList = createSampleUserList();
        when(userService.getAllUser()).thenReturn(authUserList);

        // Act
        ResponseEntity result = userController.getAllUser();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(authUserList, result.getBody());
    }

    @Test
    void testGetAllUser_Exception() {
        // Arrange
        when(userService.getAllUser()).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity result = userController.getAllUser();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testGetUserById_Success() {
        // Arrange
        long userId = 1;
        Optional<AuthUser> expectedUser = Optional.of(createSampleUser());
        when(userService.getUserById(userId)).thenReturn(expectedUser);

        // Act
        ResponseEntity<Optional<AuthUser>> result = userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedUser, result.getBody());
    }

    @Test
    void testGetUserById_NotFound() {
        // Arrange
        long userId = 1;
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Optional<AuthUser>> result = userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testUpdateUser_Success() {
        // Arrange
        AuthUser authUserToUpdate = createSampleUser();
        when(userService.patchUser(authUserToUpdate)).thenReturn(Optional.of(authUserToUpdate));

        // Act
        ResponseEntity result = userController.updateUser(authUserToUpdate);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testUpdateUser_BadRequest() {
        // Arrange
        AuthUser authUserToUpdate = createSampleUser();
        when(userService.patchUser(authUserToUpdate)).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity result = userController.updateUser(authUserToUpdate);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void testDeleteUser_Success() {
        // Arrange
        long userId = 1;

        // Act
        ResponseEntity<String> result = userController.deleteUser(userId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("user deleted", result.getBody());
    }

    // Help Method

    private AuthUser createSampleUser() {
        AuthUser authUser = new AuthUser("ee", "ee");
        return authUser;
    }

    private List<AuthUser> createSampleUserList() {

        List<AuthUser> authUserList = new ArrayList<>();
        authUserList.add(createSampleUser());
        return authUserList;
    }
}
