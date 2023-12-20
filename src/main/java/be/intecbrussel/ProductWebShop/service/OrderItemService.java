package be.intecbrussel.ProductWebShop.service;

import be.intecbrussel.ProductWebShop.exception.OrderItemNotFoundExp;
import be.intecbrussel.ProductWebShop.model.AuthUser;
import be.intecbrussel.ProductWebShop.model.Order;
import be.intecbrussel.ProductWebShop.model.OrderItem;
import be.intecbrussel.ProductWebShop.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    // properties
    private OrderItemRepository orderItemRepository;
    // private OrderService orderService;
    private UserService userService;

    // constructor

    public OrderItemService(OrderItemRepository orderItemRepository, UserService userService) {
        this.orderItemRepository = orderItemRepository;
        this.userService = userService;
        //  this.orderService = orderService;
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

    public List<OrderItem> getOrderItemListByOrderId(long id) {
        return orderItemRepository.findOrderItemsByOrder_Id(id);
    }

    // getOrderItemByUserId
    public List<OrderItem> getOrderItemByUserId(long userId) {


        AuthUser authUserDb = userService.getUserById(userId).get();

        List<OrderItem> orderItemList = new ArrayList<>();

        // List<Order> orderList = orderService.getOrderByUserId(authUserDb.getId());
        //  for (Order order : orderList) {
        //      List<OrderItem> orderItems = orderItemRepository.findOrderItemsByOrder_Id(order.getId());
        //      for (OrderItem orderItem : orderItems) {
        //          orderItemList.add(orderItem);
        //      }
        //  }
        return orderItemList;
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
