package be.intecbrussel.ProductWebShop.controller;

import be.intecbrussel.ProductWebShop.dto.AuthDto.LoginRequest;
import be.intecbrussel.ProductWebShop.dto.AuthDto.LoginResponse;
import be.intecbrussel.ProductWebShop.model.User;
import be.intecbrussel.ProductWebShop.service.RegisterService;
import be.intecbrussel.ProductWebShop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/authController")
public class AuthController {

    // properties
    private RegisterService registerService;
    private UserService userService;

    // constructors
    public AuthController(RegisterService registerService, UserService userService) {
        this.registerService = registerService;
        this.userService = userService;
    }

    // custom methods
    // register
    @PostMapping("/register")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        Optional<User> result = registerService.createUser(user);

        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result.get());
    }

    // login
    @PostMapping("/loginUser")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {

        //getting registered user by email
        Optional<User> userFromDb = userService.getUser(loginRequest.getEmail());

        //if user isn't registered
        if (userFromDb.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //if passwords don't match
        if (!userFromDb.get().getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        LoginResponse loginResponse = new LoginResponse(userFromDb.get().getId(), userFromDb.get().getEmail(), userFromDb.get().getPassword());

        return ResponseEntity.ok(loginResponse);
    }
}
