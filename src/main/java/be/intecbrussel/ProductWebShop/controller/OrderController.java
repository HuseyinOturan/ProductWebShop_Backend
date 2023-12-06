package be.intecbrussel.ProductWebShop.controller;

import be.intecbrussel.ProductWebShop.dto.OrderFront;
import be.intecbrussel.ProductWebShop.dto.OrderItemFront;
import be.intecbrussel.ProductWebShop.dto.OrderItemRequest;
import be.intecbrussel.ProductWebShop.dto.OrderRequest;
import be.intecbrussel.ProductWebShop.exception.OrderItemNotFoundExp;
import be.intecbrussel.ProductWebShop.exception.OrderNotFoundExp;
import be.intecbrussel.ProductWebShop.model.Order;
import be.intecbrussel.ProductWebShop.model.OrderItem;
import be.intecbrussel.ProductWebShop.model.Product;
import be.intecbrussel.ProductWebShop.model.User;
import be.intecbrussel.ProductWebShop.service.OrderService;
import be.intecbrussel.ProductWebShop.service.ProductService;
import be.intecbrussel.ProductWebShop.service.UserService;
import org.springframework.boot.web.reactive.filter.OrderedHiddenHttpMethodFilter;
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
    @PostMapping("addOrder")
    public ResponseEntity addOrder(@RequestBody OrderFront orderFront) {
        Optional<User> dbUser = userService.getUser(orderFront.getUserEmail());
        if (dbUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<OrderItemRequest> orderItemRequestList = new ArrayList<>();

        for (OrderItemFront orderItemFront : orderFront.getOrderItemFrontList()) {

            Optional<Product> dbProduct = productService.getProductByName(orderItemFront.getProductName());

            if (dbProduct.isEmpty()) {
                ResponseEntity.notFound().build();
            }
            orderItemRequestList.add(new OrderItemRequest(dbProduct.get().getId(), orderItemFront.getAmonut()));
        }

        OrderRequest orderRequest = new OrderRequest(dbUser.get().getId(), orderItemRequestList);

        boolean success = orderService.addOrder(orderRequest);
        if (success) {
            return ResponseEntity.ok("order has been created.");
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
    public ResponseEntity<List<Order>> getOrderByUserId(@RequestParam Long userid) {
        try {
            List<Order> orders = orderService.getOrderByUserId(userid);
            return ResponseEntity.ok(orders);
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
        } catch (OrderNotFoundExp | OrderItemNotFoundExp e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // getOrderTotalPrice
    @GetMapping("/getOrderTotalPriceByOrderId")
    public ResponseEntity<Double> getOrderTotalPrice(@RequestParam Long orderId) {
        try {
            double totalprice = orderService.getOrderTotalPrice(orderId);
            return ResponseEntity.ok(totalprice);
        } catch (OrderNotFoundExp e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
