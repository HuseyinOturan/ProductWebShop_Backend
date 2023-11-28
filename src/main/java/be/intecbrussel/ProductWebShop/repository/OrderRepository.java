package be.intecbrussel.ProductWebShop.repository;

import be.intecbrussel.ProductWebShop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrdersByUser_Id(Long userid);
}
