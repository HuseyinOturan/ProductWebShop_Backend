package be.intecbrussel.ProductWebShop.controller;

import be.intecbrussel.ProductWebShop.dto.AuthDto.LoginRequest;
import be.intecbrussel.ProductWebShop.dto.AuthDto.LoginResponse;
import be.intecbrussel.ProductWebShop.dto.AuthDto.RegisterRequest;
import be.intecbrussel.ProductWebShop.model.AuthUser;
import be.intecbrussel.ProductWebShop.service.ProductService;
import be.intecbrussel.ProductWebShop.service.RegisterService;
import be.intecbrussel.ProductWebShop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    private ProductService productService;

    // constructors


    public AuthController(RegisterService registerService, UserService userService, ProductService productService) {
        this.registerService = registerService;
        this.userService = userService;
        this.productService = productService;
    }

    // custom methods
    // register
    @PostMapping("/register")
    public ResponseEntity<AuthUser> saveUser(@RequestBody RegisterRequest registerRequest) {
        Optional<AuthUser> result = registerService.createUser(registerRequest);

        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result.get());
    }

    // login
    @PostMapping("/loginUser")
    public ResponseEntity loginUser(@RequestBody LoginRequest loginRequest) {
        Optional<LoginResponse> loginResponse = userService.login(loginRequest);
        if (loginResponse.isPresent()) {
            return ResponseEntity.ok(loginResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getAllProduct")
    public ResponseEntity getAllProduct() {
        try {
            return ResponseEntity.ok(productService.getAllProduct());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
