package be.intecbrussel.ProductWebShop.controller;

import be.intecbrussel.ProductWebShop.exception.UserNotFoundExp;
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
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {

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

    // update
    @PatchMapping("/updateUser")
    public ResponseEntity updateUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.patchUser(user));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // delete
    @DeleteMapping("/deleteUserByUserId")
    public ResponseEntity<String> deleteUser(@RequestParam Long userId) {
        try {
            userService.userDeleteByUserId(userId);
            return ResponseEntity.ok("user deleted");
        } catch (UserNotFoundExp e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}







