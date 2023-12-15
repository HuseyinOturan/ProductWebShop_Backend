package be.intecbrussel.ProductWebShop.service;

import be.intecbrussel.ProductWebShop.dto.OrderDto.OrderApp;
import be.intecbrussel.ProductWebShop.dto.OrderDto.OrderItemApp;
import be.intecbrussel.ProductWebShop.dto.OrderDto.OrderItemRequest;
import be.intecbrussel.ProductWebShop.dto.OrderDto.OrderRequest;
import be.intecbrussel.ProductWebShop.exception.OrderItemNotFoundExp;
import be.intecbrussel.ProductWebShop.exception.OrderNotFoundExp;
import be.intecbrussel.ProductWebShop.model.Order;
import be.intecbrussel.ProductWebShop.model.OrderItem;
import be.intecbrussel.ProductWebShop.model.Product;
import be.intecbrussel.ProductWebShop.model.User;
import be.intecbrussel.ProductWebShop.repository.OrderRepository;
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

    public boolean addOrder(OrderApp orderApp) {

        // getter orderRequest and orderItemRequest from orderApp

        User user1 = userService.getUserById(orderApp.getId()).get();

        List<OrderItemRequest> orderItemRequestList1 = new ArrayList<>();
        for (OrderItemApp orderItemApp : orderApp.getOrderItemAppList()) {

            Optional<Product> productDb = productService.getProductById(orderItemApp.getProductAppId());
            if (productDb.isEmpty()) {
                return false;
            }
            orderItemRequestList1.add(new OrderItemRequest(productDb.get().getId(), orderItemApp.getAmount()));
        }

        OrderRequest orderRequest = new OrderRequest(user1.getId(), orderItemRequestList1);

        System.err.println(" order ");

        try {


            // control for orderItemList.
            if (orderRequest.getOrderItemRequestList().isEmpty() || orderRequest.getOrderItemRequestList() == null) {
                return false;
            }
            // control for product en quantity
            for (OrderItemRequest orderItemRequest : orderRequest.getOrderItemRequestList()) {
                if (orderItemRequest.getProductId() == null || orderItemRequest.getQuantity() < 1) {
                    return false;
                }
            }
            // get User from Database
            Optional<User> userFromDatabase = userService.getUserById(orderRequest.getUserId());
            if (userFromDatabase.isEmpty()) {
                return false;
            }
            // get Product from Database and new orderItemList
            List<OrderItem> orderItemList = new ArrayList<>();

            for (OrderItemRequest orderItemRequest : orderRequest.getOrderItemRequestList()) {

                // get product from database
                Optional<Product> productDatabase = productService.getProductById(orderItemRequest.getProductId());

                if (productDatabase.isEmpty()) {
                    return false;
                }
                // stock control
                if (orderItemRequest.getQuantity() > productDatabase.get().getStock()) {
                    return false;
                }
                // add orderItemList
                orderItemList.add(new OrderItem(productDatabase.get(), orderItemRequest.getQuantity()));
            } // save order
            Order order1 = new Order(userFromDatabase.get(), orderItemList);
            orderRepository.save(order1);

            // save orderItem with order
            for (OrderItem orderItem : orderItemList) {
                orderItemService.addOrderItem(new OrderItem(orderItem.getProduct(), orderItem.getQuantity(), order1));
            }
            // stock reduction
            for (OrderItem orderItem : order1.getOrderItemList()) {
                stockReduction(orderItem.getProduct(), orderItem.getQuantity());
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // product reduction
    private void stockReduction(Product product, double quantity) {
        double stock = product.getStock() - quantity;
        product.setStock(stock);
        productService.patchProduct(product);
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
    public void deleteOrderByid(Long id) throws OrderNotFoundExp, OrderItemNotFoundExp {
        Optional<Order> orderDatabase = orderRepository.findById(id);

        if (orderDatabase.isPresent()) {
            // orderItem delete
            List<OrderItem> orderItemList = orderItemService.getOrderItemListByOrderId(orderDatabase.get().getId());

            for (OrderItem orderItem : orderItemList) {
                orderItemService.deleteOrderItemById(orderItem.getId());
            }
            // order delete
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

