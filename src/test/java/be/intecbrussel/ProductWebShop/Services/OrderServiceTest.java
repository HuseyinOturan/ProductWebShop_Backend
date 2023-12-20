package be.intecbrussel.ProductWebShop.Services;

import be.intecbrussel.ProductWebShop.exception.OrderNotFoundExp;
import be.intecbrussel.ProductWebShop.model.Order;
import be.intecbrussel.ProductWebShop.model.OrderItem;
import be.intecbrussel.ProductWebShop.model.Product;
import be.intecbrussel.ProductWebShop.model.AuthUser;
import be.intecbrussel.ProductWebShop.repository.OrderRepository;
import be.intecbrussel.ProductWebShop.service.OrderItemService;
import be.intecbrussel.ProductWebShop.service.OrderService;
import be.intecbrussel.ProductWebShop.service.ProductService;
import be.intecbrussel.ProductWebShop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @Mock
    private OrderItemService orderItemService;

    @Mock
    private UserService userService;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllOrder() {
        // Arrange
        List<Order> orderList = createSampleOrderList();
        when(orderRepository.findAll()).thenReturn(orderList);

        // Act
        List<Order> result = orderService.getAllOrder();

        // Assert
        assertEquals(orderList, result);
    }

    @Test
    void testGetOrderById_Success() {
        // Arrange
        long orderId = 1;
        Order expectedOrder = createSampleOrder();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(expectedOrder));

        // Act
        Optional<Order> result = orderService.getOrderById(orderId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedOrder, result.get());
    }

    @Test
    void testGetOrderById_NotFound() {
        // Arrange
        long orderId = 1;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act
        Optional<Order> result = orderService.getOrderById(orderId);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetOrderByUserId() {
        // Arrange
        long userId = 1;
        List<Order> orderList = createSampleOrderList();
        when(orderRepository.findOrdersByAuthUser_Id(userId)).thenReturn(orderList);

        // Act
        List<Order> result = orderService.getOrderByUserId(userId);

        // Assert
        assertEquals(orderList, result);
    }

//    @Test
//    void testDeleteOrderByid_Success() throws OrderNotFoundExp, OrderItemNotFoundExp {
//        // Arrange
//        long orderId = 1;
//        Order orderToDelete = createSampleOrder();
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderToDelete));
//
//        // Act
//        orderService.deleteOrderByid(orderId);
//
//        // Assert
//        Mockito.verify(orderItemService, Mockito.times(1)).deleteOrderItemById(anyLong());
//        Mockito.verify(orderRepository, Mockito.times(1)).deleteById(orderId);
//    }

    @Test
    void testDeleteOrderByid_OrderNotFound() {
        // Arrange
        long orderId = 1;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(OrderNotFoundExp.class, () -> orderService.deleteOrderByid(orderId));
    }

//    @Test
//    void testDeleteOrderByid_OrderItemNotFound() throws OrderNotFoundExp, OrderItemNotFoundExp {
//        // Arrange
//        long orderId = 1;
//        Order orderToDelete = createSampleOrder();
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderToDelete));
//        when(orderItemService.getOrderItemListByOrderId(orderId)).thenReturn(new ArrayList<>());
//
//        // Act and Assert
//        assertThrows(OrderItemNotFoundExp.class, () -> orderService.deleteOrderByid(orderId));
//    }

    // Help method

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
