package be.intecbrussel.ProductWebShop.controller;

import be.intecbrussel.ProductWebShop.exception.UserNotFoundExp;
import be.intecbrussel.ProductWebShop.model.AuthUser;
import be.intecbrussel.ProductWebShop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
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
    public ResponseEntity<AuthUser> getUserByEmail(@RequestParam String email) {

        Optional<AuthUser> userFromDb = userService.getUser(email);
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
    public ResponseEntity<Optional<AuthUser>> getUserById(@RequestParam long id) {

        Optional<AuthUser> dbUser = userService.getUserById(id);
        if (dbUser.isPresent()) {
            return ResponseEntity.ok(dbUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // update
    @PatchMapping("/updateUser")
    public ResponseEntity updateUser(@RequestBody AuthUser authUser) {
        try {
            return ResponseEntity.ok(userService.patchUser(authUser));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // delete
    @DeleteMapping("/deleteUserByUserId")
    public ResponseEntity<String> deleteUser(@RequestParam long userId) {
        try {
            userService.userDeleteByUserId(userId);
            return ResponseEntity.ok("user deleted");
        } catch (UserNotFoundExp e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}







