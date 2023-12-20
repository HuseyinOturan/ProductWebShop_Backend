package be.intecbrussel.ProductWebShop.security;

import be.intecbrussel.ProductWebShop.model.AuthUser;
import be.intecbrussel.ProductWebShop.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AuthUser> userDatabase = userRepository.findByEmail(email);

        if (userDatabase.isEmpty()) {
            throw new UsernameNotFoundException(email + "not found.");
        }
//        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
//                .username(userDatabase.get().getEmail())
//                .password(userDatabase.get().getPassword())
//                .roles(userDatabase.get().isAdmin() ? "ADMIN" : "USER")
//                .build();

        return User.builder().username(userDatabase.get().getEmail())
                .password(userDatabase.get().getPassword())
                .roles(userDatabase.get().isAdmin() ? "ADMIN" : "USER")
                .build();
    }
}
