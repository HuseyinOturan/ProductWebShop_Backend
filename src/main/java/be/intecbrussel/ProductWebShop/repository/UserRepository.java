package be.intecbrussel.ProductWebShop.repository;

import be.intecbrussel.ProductWebShop.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AuthUser, Long> {

    //    @Query("SELECT u FROM AuthUser u WHERE u.email = :email")
    Optional<AuthUser> findByEmail(String email);
}
