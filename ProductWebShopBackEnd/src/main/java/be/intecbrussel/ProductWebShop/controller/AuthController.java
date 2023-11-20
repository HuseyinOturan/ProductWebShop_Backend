package be.intecbrussel.ProductWebShop.controller;

import be.intecbrussel.ProductWebShop.dto.LoginAttempt;
import be.intecbrussel.ProductWebShop.dto.LoginRequest;
import be.intecbrussel.ProductWebShop.dto.LoginResponse;
import be.intecbrussel.ProductWebShop.model.User;
import be.intecbrussel.ProductWebShop.service.RegisterService;
import be.intecbrussel.ProductWebShop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class AuthController {

    // properties
    private  RegisterService registerService;
    private UserService userService;

    // constructors
    public AuthController(RegisterService registerService, UserService userService){
        this.registerService=registerService;
        this.userService=userService;
    }

    // custom methods

    // register
    @PostMapping("/register")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        Optional<User> result= registerService.createUser(user);

        if (result.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result.get());
    }

    // login
    @PostMapping("/loginUser")
    public ResponseEntity loginUser(@RequestBody LoginAttempt loginAttempt){

        //getting registered user by email
        Optional<User> userFromDb = userService.getUser(loginAttempt.getEmail());

        //if user isn't registered
        if (userFromDb.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //if passwords don't match
        if (!userFromDb.get().getPassword().equals(loginAttempt.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userFromDb.get());
    }
}
