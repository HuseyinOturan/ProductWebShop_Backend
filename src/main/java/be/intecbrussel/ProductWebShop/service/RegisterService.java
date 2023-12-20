package be.intecbrussel.ProductWebShop.service;

import be.intecbrussel.ProductWebShop.dto.AuthDto.RegisterRequest;
import be.intecbrussel.ProductWebShop.model.AuthUser;
import be.intecbrussel.ProductWebShop.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterService {

    // properties
    private UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // constructors

    public RegisterService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // custom methods

    // create user
    public Optional<AuthUser> createUser(RegisterRequest registerRequest) {

        AuthUser authUser = new AuthUser(registerRequest.getEmail(), registerRequest.getPassword());

        // input validation

        if (authUser.getEmail().trim().isEmpty() || authUser.getPassword().trim().isEmpty()) {
            return Optional.empty();
        }
        // check dub. email
        Optional<AuthUser> userFromDb = userRepository.findByEmail(authUser.getEmail());
        if (userFromDb.isPresent()) {
            return Optional.empty();
        }
        // save user in db with cryptPassword

        AuthUser saveAuthUser = new AuthUser(authUser.getEmail(), bCryptPasswordEncoder.encode(authUser.getPassword()));
        return Optional.of(userRepository.save(saveAuthUser));

    }
}
