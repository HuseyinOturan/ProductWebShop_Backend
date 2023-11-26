package be.intecbrussel.ProductWebShop.service;

import be.intecbrussel.ProductWebShop.dto.LoginRequest;
import be.intecbrussel.ProductWebShop.dto.LoginResponse;
import be.intecbrussel.ProductWebShop.model.User;
import be.intecbrussel.ProductWebShop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterService {

    // properties
    private UserRepository userRepository;

    // constructors
    public RegisterService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    // custom methods

    // create user
    public Optional<User> createUser(User user){
        // input validation
        if(user.getEmail().trim().isEmpty() || user.getPassword().trim().isEmpty()){
            return Optional.empty();
        }
        // check dub. email
        Optional<User> userFromDb= userRepository.findByEmail(user.getEmail());

        if(userFromDb.isPresent()) {
            return Optional.empty();
        }
        // save user in db
        return Optional.of(userRepository.save(user));

    }
}
