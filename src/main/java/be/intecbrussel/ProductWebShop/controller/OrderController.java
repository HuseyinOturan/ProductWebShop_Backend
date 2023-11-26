package be.intecbrussel.ProductWebShop.controller;

import be.intecbrussel.ProductWebShop.dto.OrderItemRequest;
import be.intecbrussel.ProductWebShop.dto.OrderRequest;
import be.intecbrussel.ProductWebShop.exception.OrderNotFoundExp;
import be.intecbrussel.ProductWebShop.model.Order;
import be.intecbrussel.ProductWebShop.model.OrderItem;
import be.intecbrussel.ProductWebShop.model.Product;
import be.intecbrussel.ProductWebShop.model.User;
import be.intecbrussel.ProductWebShop.service.OrderService;
import be.intecbrussel.ProductWebShop.service.ProductService;
import be.intecbrussel.ProductWebShop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    // properties
    private OrderService orderService;
    private ProductService productService;
    private UserService userService;

    // constructors

    public OrderController(OrderService orderService, ProductService productService, UserService userService) {
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
    }

    // custom methods
    // create
    @PostMapping("/addOrder")
    public ResponseEntity addOrder(@RequestBody OrderRequest orderRequest) {

        // get User from Database
        Optional<User> user = userService.getUserById(orderRequest.getUserId());
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // get Product from Database
        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderItemRequest orderItemRequest : orderRequest.getOrderItemRequestList()) {
            Optional<Product> productDatabase = productService.getProductById(orderItemRequest.getProductId());
            if (productDatabase.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            orderItemList.add(new OrderItem(productDatabase.get(), orderItemRequest.getQuantity()));
        }
        // order
        Order order = new Order(user.get(), orderItemList);

        boolean success = orderService.addOrder(order);
        if (success) {
            double totalPrice = orderTotalPrice(order.getId());
            return ResponseEntity.ok("order has been created. Total price : " + totalPrice);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There was an error while creating the order");
        }
    }

    // read
    @GetMapping("/getAllOrder")
    public ResponseEntity getAllOrder() {
        try {
            return ResponseEntity.ok(orderService.getAllOrder());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getOrderById")
    public ResponseEntity<Optional<Order>> getOrderById(@RequestParam Long id) {
        Optional<Order> order = orderService.getOrderById(id);

        if (order.isPresent()) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getOrderByUserId")
    public ResponseEntity getOrderByUserId(@RequestParam Long userid) {
        try {
            return ResponseEntity.ok(orderService.getOrderByUserId(userid));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }
    // delete

    @DeleteMapping("deleteOrderById")
    public ResponseEntity<String> deleteOrderById(@RequestParam Long id) {
        try {
            orderService.deleteOrderByid(id);
            return ResponseEntity.ok("Order deleted successfully");
        } catch (OrderNotFoundExp e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // getOrderTotalPrice
    @GetMapping("getOrderTotalPriceByOrderId")
    public ResponseEntity<Double> getOrderTotalPrice(@RequestParam Long orderId) {
        try {
            double totalprice = orderService.getOrderTotalPrice(orderId);
            return ResponseEntity.ok(totalprice);
        } catch (OrderNotFoundExp e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private double orderTotalPrice(Long orderId) {

        double totalPrice = 0;

        Optional<Order> dbOrder = orderService.getOrderById(orderId);

        for (OrderItem orderItem : dbOrder.get().getOrderItemList()) {
            totalPrice += orderItem.getProduct().getPrice() * orderItem.getQuantity();
        }
        return totalPrice;
    }
}
