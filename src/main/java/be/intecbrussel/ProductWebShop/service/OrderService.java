package be.intecbrussel.ProductWebShop.service;

import be.intecbrussel.ProductWebShop.exception.OrderNotFoundExp;
import be.intecbrussel.ProductWebShop.model.Order;
import be.intecbrussel.ProductWebShop.model.OrderItem;
import be.intecbrussel.ProductWebShop.model.Product;
import be.intecbrussel.ProductWebShop.model.User;
import be.intecbrussel.ProductWebShop.repository.OrderItemRepository;
import be.intecbrussel.ProductWebShop.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    // properties
    private OrderRepository orderRepository;
    private ProductService productService;
    private OrderItemService orderItemService;

    private UserService userService;

    // constructors
    public OrderService(OrderRepository orderRepository, ProductService productService, OrderItemService orderItemService, UserService userService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.orderItemService = orderItemService;
        this.userService = userService;
    }
    // custom methods

    // create
    public boolean addOrder(Order order) {

        try {


            // get user from database

            User userFromDatabase = userService.getUserById(order.getUser().getId()).get();

            // control for orderItemList.
            if (order.getOrderItemList().isEmpty() || order.getOrderItemList() == null) {
                return false;
            }
            // control for product en quantitiy

            for (OrderItem orderItem : order.getOrderItemList()) {
                if (orderItem.getProduct() == null || orderItem.getQuantity() < 1) {
                    return false;
                }
            }
            List<OrderItem> orderItemList = new ArrayList<>();

            for (OrderItem orderItem : order.getOrderItemList()) {

                // stock control
                Optional<Product> productFromData = productService.getProductById(orderItem.getProduct().getId());
                if (productFromData.isEmpty()) {
                    return false;
                }
                if (productFromData.get().getStock() <= orderItem.getQuantity()) {
                    return false;
                }
                // save orderItem
                OrderItem orderItem1 = new OrderItem(productFromData.get(), orderItem.getQuantity());
                orderItemService.addOrderItem(orderItem1);
                orderItemList.add(orderItem);
            }
            //sava order
            Order order1 = new Order(userFromDatabase, orderItemList);
            orderRepository.save(order1);

            // stock reduction
            for (OrderItem orderItem : order.getOrderItemList()) {
                double oldStock = orderItem.getProduct().getStock();
                double newStock = oldStock - orderItem.getQuantity();
                orderItem.getProduct().setStock(newStock);
                productService.patchProduct(orderItem.getProduct());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // read
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrderByUserId(Long userId) {
        return orderRepository.findOrdersByUser_Id(userId);

    }

    // delete
    public void deleteOrderByid(Long id) throws OrderNotFoundExp {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new OrderNotFoundExp("Order not found with id: " + id);
        }
    }

    // getOrderTotalPrice
    public double getOrderTotalPrice(Long orderId) throws OrderNotFoundExp {

        Optional<Order> dbOrder = orderRepository.findById(orderId);

        if (dbOrder.isEmpty()) {
            throw new OrderNotFoundExp("Not found");
        }
        double totalOrderPrice = 0;
        for (OrderItem orderItem : dbOrder.get().getOrderItemList()) {
            totalOrderPrice += orderItem.getQuantity() * orderItem.getProduct().getPrice();
        }
        return totalOrderPrice;
    }
}

