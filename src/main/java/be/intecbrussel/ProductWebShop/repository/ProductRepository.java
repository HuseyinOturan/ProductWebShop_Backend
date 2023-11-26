package be.intecbrussel.ProductWebShop.repository;

import be.intecbrussel.ProductWebShop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
