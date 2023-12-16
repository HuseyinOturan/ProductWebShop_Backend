package be.intecbrussel.ProductWebShop.Controller;

import be.intecbrussel.ProductWebShop.controller.UserController;
import be.intecbrussel.ProductWebShop.exception.UserNotFoundExp;
import be.intecbrussel.ProductWebShop.model.User;
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

public class UserControllerTest {

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
        User expectedUser = createSampleUser();
        when(userService.getUser(userEmail)).thenReturn(Optional.of(expectedUser));

        // Act
        ResponseEntity<User> result = userController.getUserByEmail(userEmail);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedUser, result.getBody());
    }

    @Test
    void testGetUserByEmail_NotFound() {
        // Arrange
        String userEmail = "nonexistent@example.com";
        when(userService.getUser(userEmail)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> result = userController.getUserByEmail(userEmail);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testGetAllUser_Success() {
        // Arrange
        List<User> userList = createSampleUserList();
        when(userService.getAllUser()).thenReturn(userList);

        // Act
        ResponseEntity result = userController.getAllUser();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(userList, result.getBody());
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
        Optional<User> expectedUser = Optional.of(createSampleUser());
        when(userService.getUserById(userId)).thenReturn(expectedUser);

        // Act
        ResponseEntity<Optional<User>> result = userController.getUserById(userId);

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
        ResponseEntity<Optional<User>> result = userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testUpdateUser_Success() {
        // Arrange
        User userToUpdate = createSampleUser();
        when(userService.patchUser(userToUpdate)).thenReturn(Optional.of(userToUpdate));

        // Act
        ResponseEntity result = userController.updateUser(userToUpdate);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testUpdateUser_BadRequest() {
        // Arrange
        User userToUpdate = createSampleUser();
        when(userService.patchUser(userToUpdate)).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity result = userController.updateUser(userToUpdate);

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

    private User createSampleUser() {
        User user = new User("ee", "ee");
        return user;
    }

    private List<User> createSampleUserList() {

        List<User> userList = new ArrayList<>();
        userList.add(createSampleUser());
        return userList;
    }
}
