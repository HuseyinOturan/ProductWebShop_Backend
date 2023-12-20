package be.intecbrussel.ProductWebShop.Services;

import be.intecbrussel.ProductWebShop.exception.UserNotFoundExp;
import be.intecbrussel.ProductWebShop.model.AuthUser;
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
import static org.mockito.Mockito.when;

public class AuthUserServiceTest {
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
        AuthUser expectedAuthUser = new AuthUser("userMail", "password");
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(expectedAuthUser));

        // Act
        Optional<AuthUser> actualUser = userService.getUser(userEmail);

        // Assert
        assertTrue(actualUser.isPresent());
        assertEquals(expectedAuthUser, actualUser.get());


        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(userEmail);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<AuthUser> expectedAuthUsers = Arrays.asList(new AuthUser("ss", "ll"), new AuthUser("pp", "kk"), new AuthUser("mm", "oo"));
        when(userRepository.findAll()).thenReturn(expectedAuthUsers);

        // Act
        List<AuthUser> actualAuthUsers = userService.getAllUser();

        // Assert
        assertEquals(expectedAuthUsers.size(), actualAuthUsers.size());

        // control call userRepository
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        // Arrange
        Long userId = 1L;
        AuthUser expectedAuthUser = new AuthUser("g", "o");
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedAuthUser));

        // Act
        Optional<AuthUser> actualUser = userService.getUserById(userId);

        // Assert
        assertTrue(actualUser.isPresent());
        assertEquals(expectedAuthUser, actualUser.get());


        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
    }

    @Test
    void testPatchUser() {
        // Arrange
        AuthUser authUserToUpdate = new AuthUser("l", "l");
        when(userRepository.save(authUserToUpdate)).thenReturn(authUserToUpdate);

        // Act
        Optional<AuthUser> updatedUser = userService.patchUser(authUserToUpdate);

        // Assert
        assertTrue(updatedUser.isPresent());
        assertEquals(authUserToUpdate, updatedUser.get());


        Mockito.verify(userRepository, Mockito.times(1)).save(authUserToUpdate);
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

