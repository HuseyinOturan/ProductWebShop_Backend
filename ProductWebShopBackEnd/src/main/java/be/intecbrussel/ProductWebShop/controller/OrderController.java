package be.intecbrussel.ProductWebShop.controller;

import be.intecbrussel.ProductWebShop.exception.OrderNotFoundExp;
import be.intecbrussel.ProductWebShop.model.Order;
import be.intecbrussel.ProductWebShop.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    // properties

    private OrderService orderService;

    // constructors

    public OrderController(OrderService orderService){
        this.orderService=orderService;
    }
    // custom methods
    // create
    @PostMapping("/addOrder")
    public ResponseEntity<?> addOrder(@RequestBody Order order){

        try {
            boolean success = orderService.addOrder(order);
            if (success){
                return ResponseEntity.ok("order has been created");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There was an error while creating the order");
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid request" + e.getMessage());
        }
    }
    // read

    @GetMapping("/getAllOrder")
    public ResponseEntity getAllOrder(){
        try {
            return ResponseEntity.ok(orderService.getAllOrder());
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getOrderById")
    public ResponseEntity<Optional<Order>> getOrderById(@RequestParam Long id){
        Optional<Order> order= orderService.getOrderById(id);

        if (order.isPresent()){
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getOrderByUserId")
    public ResponseEntity getOrderByUserId(@RequestParam Long userid){
       try {
           return ResponseEntity.ok(orderService.getOrderByUserId(userid));
       } catch (Exception e){
           return ResponseEntity.notFound().build();
       }

    }
    // delete

    @DeleteMapping("deleteOrderById")
    public ResponseEntity<String> deleteOrderById(@RequestParam Long id){
        try {
            orderService.deleteOrderByid(id);
            return ResponseEntity.ok("Order deleted successfully");
        }
        catch (OrderNotFoundExp e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }



}
