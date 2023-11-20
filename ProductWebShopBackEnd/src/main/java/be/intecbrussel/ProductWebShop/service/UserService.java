package be.intecbrussel.ProductWebShop.service;

import be.intecbrussel.ProductWebShop.repository.UserRepository;
import org.springframework.stereotype.Service;
import be.intecbrussel.ProductWebShop.model.User;

import java.util.Optional;

@Service
public class UserService {

    // propertires
    private UserRepository userRepository;

    // constructors

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    // custom methods
    // CREATE


    // READ
    public Optional<User> getUser(String email){
        return userRepository.findByEmail(email);
    }


}
