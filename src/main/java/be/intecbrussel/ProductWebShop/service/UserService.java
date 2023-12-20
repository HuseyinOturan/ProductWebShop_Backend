package be.intecbrussel.ProductWebShop.service;

import be.intecbrussel.ProductWebShop.dto.AuthDto.LoginRequest;
import be.intecbrussel.ProductWebShop.dto.AuthDto.LoginResponse;
import be.intecbrussel.ProductWebShop.exception.UserNotFoundExp;
import be.intecbrussel.ProductWebShop.model.AuthUser;
import be.intecbrussel.ProductWebShop.repository.UserRepository;
import be.intecbrussel.ProductWebShop.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    // propertires
    private UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // constructors


    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Optional<LoginResponse> login(LoginRequest loginRequest) {
        Optional<AuthUser> opAuthUser = getUser(loginRequest.getEmail());

        if (opAuthUser.isEmpty()) {
            return Optional.empty();
        }

        AuthUser authUser = opAuthUser.get();

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        String email = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = "USER";

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                role = "ADMIN";
                break;
            }
        }
        String token = jwtUtil.createToken(authUser, role);
        LoginResponse loginResponse = new LoginResponse(authUser.getId(), email, role, token);
        return Optional.of(loginResponse);
    }

    // custom methods
    // CREATE in registerserice

    // READ
    public Optional<AuthUser> getUser(String email) {
        return userRepository.findByEmail(email);
    }

    public List<AuthUser> getAllUser() {
        return userRepository.findAll();
    }

    public Optional<AuthUser> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // update
    public Optional<AuthUser> patchUser(AuthUser authUser) {
        return Optional.of(userRepository.save(authUser));
    }

    // delete
    public void userDeleteByUserId(Long userId) throws UserNotFoundExp {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new UserNotFoundExp("user not found");
        }
    }
}
