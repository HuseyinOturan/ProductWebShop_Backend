package be.intecbrussel.ProductWebShop.repository;

import be.intecbrussel.ProductWebShop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // @Query("SELECT u FROM User u WHERE u.email = :email")
    // Optional<User> findByEmail(String email);

    //    @Query("SELECT p FROM  Product p WHERE p.name = :name")
    Optional<Product> findByName(String name);

}
