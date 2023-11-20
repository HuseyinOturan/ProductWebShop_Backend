package be.intecbrussel.ProductWebShop.repository;

import be.intecbrussel.ProductWebShop.model.Order;
import be.intecbrussel.ProductWebShop.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
