package be.intecbrussel.ProductWebShop.Controller;

import be.intecbrussel.ProductWebShop.controller.OrderController;
import be.intecbrussel.ProductWebShop.dto.OrderDto.OrderApp;
import be.intecbrussel.ProductWebShop.dto.OrderDto.OrderItemApp;
import be.intecbrussel.ProductWebShop.exception.OrderNotFoundExp;
import be.intecbrussel.ProductWebShop.model.AuthUser;
import be.intecbrussel.ProductWebShop.model.Order;
import be.intecbrussel.ProductWebShop.model.OrderItem;
import be.intecbrussel.ProductWebShop.model.Product;
import be.intecbrussel.ProductWebShop.service.OrderService;
import be.intecbrussel.ProductWebShop.service.ProductService;
import be.intecbrussel.ProductWebShop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    @Mock
    private OrderService orderService;

    @Mock
    private ProductService productService;

    @Mock
    private UserService userService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddOrder_Success() {
        // Arrange
        OrderApp orderApp = createSampleOrderApp();
        when(orderService.addOrder(orderApp)).thenReturn(true);

        // Act
        ResponseEntity result = orderController.addOrder(orderApp);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testAddOrder_BadRequest() {
        // Arrange
        OrderApp orderApp = createSampleOrderApp();
        when(orderService.addOrder(orderApp)).thenReturn(false);

        // Act
        ResponseEntity result = orderController.addOrder(orderApp);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("There was an error while creating the order", result.getBody());
    }

    @Test
    void testGetAllOrder_Success() {
        // Arrange
        List<Order> orderList = createSampleOrderList();
        when(orderService.getAllOrder()).thenReturn(orderList);

        // Act
        ResponseEntity result = orderController.getAllOrder();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(orderList, result.getBody());
    }

    @Test
    void testGetAllOrder_Exception() {
        // Arrange
        when(orderService.getAllOrder()).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity result = orderController.getAllOrder();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testGetOrderById_Success() {
        // Arrange
        long orderId = 1;
        Optional<Order> expectedOrder = Optional.of(createSampleOrder());
        when(orderService.getOrderById(orderId)).thenReturn(expectedOrder);

        // Act
        ResponseEntity<Optional<Order>> result = orderController.getOrderById(orderId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedOrder, result.getBody());
    }

    @Test
    void testGetOrderById_NotFound() {
        // Arrange
        long orderId = 1;
        when(orderService.getOrderById(orderId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Optional<Order>> result = orderController.getOrderById(orderId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testGetOrderByUserId_Success() {
        // Arrange
        long userId = 1;
        List<Order> orderList = createSampleOrderList();
        when(orderService.getOrderByUserId(userId)).thenReturn(orderList);

        // Act
        ResponseEntity<List<Order>> result = orderController.getOrderByUserId(userId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(orderList, result.getBody());
    }

    @Test
    void testGetOrderByUserId_Exception() {
        // Arrange
        long userId = 1;
        when(orderService.getOrderByUserId(userId)).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity<List<Order>> result = orderController.getOrderByUserId(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testDeleteOrderById_Success() {
        // Arrange
        long orderId = 1;

        // Act
        ResponseEntity<String> result = orderController.deleteOrderById(orderId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Order deleted successfully", result.getBody());
    }

//    @Test
//    void testDeleteOrderById_NotFound() throws OrderNotFoundExp, OrderItemNotFoundExp {
//        // Arrange
//        long orderId = 1;
//        when(orderService.deleteOrderByid(orderId)).thenThrow(new OrderNotFoundExp("Test Exception"));
//
//        // Act
//        ResponseEntity<String> result = orderController.deleteOrderById(orderId);
//
//        // Assert
//        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
//    }

    @Test
    void testGetOrderTotalPrice_Success() throws OrderNotFoundExp {
        // Arrange
        long orderId = 1;
        double totalPrice = 100.0;
        when(orderService.getOrderTotalPrice(orderId)).thenReturn(totalPrice);

        // Act
        ResponseEntity<Double> result = orderController.getOrderTotalPrice(orderId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(totalPrice, result.getBody());
    }

    @Test
    void testGetOrderTotalPrice_NotFound() throws OrderNotFoundExp {
        // Arrange
        long orderId = 1;
        when(orderService.getOrderTotalPrice(orderId)).thenThrow(new OrderNotFoundExp("Test Exception"));

        // Act
        ResponseEntity<Double> result = orderController.getOrderTotalPrice(orderId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    // Help methods

    private OrderApp createSampleOrderApp() {
        OrderItemApp orderItemApp = new OrderItemApp(1, 1);
        List<OrderItemApp> orderItemAppList = new ArrayList<>();
        orderItemAppList.add(orderItemApp);
        OrderApp orderApp = new OrderApp(1L, orderItemAppList);
        return orderApp;
    }

    private Order createSampleOrder() {
        Order order = new Order(new AuthUser("aa", "aa"), createSampleOrderItemList());
        return order;
    }

    private List<Order> createSampleOrderList() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(createSampleOrder());
        return orderList;
    }

    private OrderItem createSampleOrderItem() {
        OrderItem orderItem = new OrderItem(new Product("gg", "uu", "yy", 1, 2), 1);
        return orderItem;
    }

    private List<OrderItem> createSampleOrderItemList() {

        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(createSampleOrderItem());
        return orderItemList;
    }
}
