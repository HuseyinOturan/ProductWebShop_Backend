package be.intecbrussel.ProductWebShop.controller;

import be.intecbrussel.ProductWebShop.dto.OrderItemDto.OrderItemRes;
import be.intecbrussel.ProductWebShop.model.OrderItem;
import be.intecbrussel.ProductWebShop.service.OrderItemService;
import be.intecbrussel.ProductWebShop.service.OrderService;
import be.intecbrussel.ProductWebShop.service.ProductService;
import be.intecbrussel.ProductWebShop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orderItem")
public class OrderItemController {

    // properties
    private OrderItemService orderItemService;

    // consturctor
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    // getter and setter
    public OrderItemService getOrderItemService() {
        return orderItemService;
    }

    public void setOrderItemService(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }
    // custom methods

    // create => in Order

    // read

    // allOrderItem
    @GetMapping("/getAllOrderItem")
    public ResponseEntity getAllOrderItem() {
        try {
            return ResponseEntity.ok(orderItemService.getAllOrderItem());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // getOrderItemByUserId
    @GetMapping("/getOrderItemUserId")
    public ResponseEntity<List<OrderItemRes>> getOrderItemByUserId(@RequestParam long userId) {


        try {
            List<OrderItem> orderItems = orderItemService.getOrderItemByUserId(userId);
            System.err.println(orderItems);
            List<OrderItemRes> orderItemResList = new ArrayList<>();
            for (OrderItem orderItem : orderItems) {
                orderItemResList.add(new OrderItemRes(orderItem.getProduct().getName(), orderItem.getProduct().getPrice(),
                        orderItem.getQuantity()));
            }
            return ResponseEntity.ok(orderItemResList);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }
}
