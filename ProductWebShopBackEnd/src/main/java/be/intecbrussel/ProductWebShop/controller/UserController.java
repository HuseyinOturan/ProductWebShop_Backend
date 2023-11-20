package be.intecbrussel.ProductWebShop.controller;

import be.intecbrussel.ProductWebShop.dto.LoginAttempt;
import be.intecbrussel.ProductWebShop.model.User;
import be.intecbrussel.ProductWebShop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    // properties
    private UserService userService;

    // constructors
    public UserController(UserService userService){
        this.userService=userService;
    }


    // custom methods
    // create

    }







