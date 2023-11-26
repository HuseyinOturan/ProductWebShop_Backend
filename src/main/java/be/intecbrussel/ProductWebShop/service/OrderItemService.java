package be.intecbrussel.ProductWebShop.service;

import be.intecbrussel.ProductWebShop.exception.OrderItemNotFoundExp;
import be.intecbrussel.ProductWebShop.exception.ProductNotFoundExp;
import be.intecbrussel.ProductWebShop.model.OrderItem;
import be.intecbrussel.ProductWebShop.model.Product;
import be.intecbrussel.ProductWebShop.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    // properties
    private OrderItemRepository orderItemRepository;

    // constructor

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    // custom method
    // create
    public void addOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    // read
    public List<OrderItem> getAllOrderItem() {
        return orderItemRepository.findAll();
    }

    public Optional<OrderItem> getOrderItemById(long id) {
        return orderItemRepository.findById(id);
    }

    // update
    public Optional<OrderItem> patchProduct(OrderItem orderItem) {

        return Optional.of(orderItemRepository.save(orderItem));
    }
    // delete

    public void deleteOrderItemById(Long id) throws OrderItemNotFoundExp {

        if (orderItemRepository.existsById(id)) {
            orderItemRepository.deleteById(id);
        } else {
            throw new OrderItemNotFoundExp("OrderItem not found is : " + id);
        }
    }


}
