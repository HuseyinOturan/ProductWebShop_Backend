package be.intecbrussel.ProductWebShop.Services;

import be.intecbrussel.ProductWebShop.model.User;
import be.intecbrussel.ProductWebShop.repository.UserRepository;
import be.intecbrussel.ProductWebShop.service.RegisterService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegisterServiceTest {

    @Test
    public void testCreateUser_SuccessfulRegistration() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        RegisterService registerService = new RegisterService(userRepository);

        User newUser = new User("test@example.com", "password");

        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(newUser)).thenReturn(newUser);

        // Act
        Optional<User> result = registerService.createUser(newUser);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(newUser, result.get());


        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(newUser.getEmail());
        Mockito.verify(userRepository, Mockito.times(1)).save(newUser);
    }
}
