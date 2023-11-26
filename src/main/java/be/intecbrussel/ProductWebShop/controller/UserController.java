package be.intecbrussel.ProductWebShop.controller;

import be.intecbrussel.ProductWebShop.dto.LoginAttempt;
import be.intecbrussel.ProductWebShop.model.User;
import be.intecbrussel.ProductWebShop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    // properties
    private UserService userService;

    // constructors
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // custom methods
    // create -> create in de AuthController

    // read
    @GetMapping("/getUserByEmail")
    public ResponseEntity getUserByEmail(@RequestParam String email) {

        Optional<User> userFromDb = userService.getUser(email);
        if (userFromDb.isEmpty()) {
            return ResponseEntity.status((HttpStatus.NOT_FOUND)).build();
        }
        return ResponseEntity.ok(userFromDb.get());
    }

    @GetMapping("/getAllUser")
    public ResponseEntity getAllUser() {
        try {
            return ResponseEntity.ok(userService.getAllUser());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getUserById")
    public ResponseEntity<Optional<User>> getUserById(@RequestParam long id) {

        Optional<User> dbUser = userService.getUserById(id);
        if (dbUser.isPresent()) {
            return ResponseEntity.ok(dbUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}







