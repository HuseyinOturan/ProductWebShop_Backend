package be.intecbrussel.ProductWebShop.repository;

import be.intecbrussel.ProductWebShop.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findOrderItemsByOrder_Id(Long id);


}
