package be.intecbrussel.ProductWebShop.Services;

import be.intecbrussel.ProductWebShop.exception.UserNotFoundExp;
import be.intecbrussel.ProductWebShop.model.User;
import be.intecbrussel.ProductWebShop.repository.UserRepository;
import be.intecbrussel.ProductWebShop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetUserByEmail() {
        // Arrange
        String userEmail = "userMail";
        User expectedUser = new User("userMail", "password");
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<User> actualUser = userService.getUser(userEmail);

        // Assert
        assertTrue(actualUser.isPresent());
        assertEquals(expectedUser, actualUser.get());


        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(userEmail);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> expectedUsers = Arrays.asList(new User("ss", "ll"), new User("pp", "kk"), new User("mm", "oo"));
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userService.getAllUser();

        // Assert
        assertEquals(expectedUsers.size(), actualUsers.size());

        // control call userRepository
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        // Arrange
        Long userId = 1L;
        User expectedUser = new User("g", "o");
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<User> actualUser = userService.getUserById(userId);

        // Assert
        assertTrue(actualUser.isPresent());
        assertEquals(expectedUser, actualUser.get());


        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
    }

    @Test
    void testPatchUser() {
        // Arrange
        User userToUpdate = new User("l", "l");
        when(userRepository.save(userToUpdate)).thenReturn(userToUpdate);

        // Act
        Optional<User> updatedUser = userService.patchUser(userToUpdate);

        // Assert
        assertTrue(updatedUser.isPresent());
        assertEquals(userToUpdate, updatedUser.get());


        Mockito.verify(userRepository, Mockito.times(1)).save(userToUpdate);
    }

    @Test
    void testUserDeleteByUserId() {
        // Arrange
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> userService.userDeleteByUserId(userId));


        Mockito.verify(userRepository, Mockito.times(1)).existsById(userId);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userId);
    }

    @Test
    void testUserDeleteByUserId_UserNotFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        // Act ve Assert
        UserNotFoundExp exception = assertThrows(UserNotFoundExp.class, () -> userService.userDeleteByUserId(userId));
        assertEquals("user not found", exception.getMessage());

        // call
        Mockito.verify(userRepository, Mockito.times(1)).existsById(userId);
        Mockito.verify(userRepository, Mockito.never()).deleteById(userId);
    }


}

