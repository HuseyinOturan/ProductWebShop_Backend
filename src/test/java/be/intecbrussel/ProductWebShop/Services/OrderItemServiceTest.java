package be.intecbrussel.ProductWebShop.Services;

import be.intecbrussel.ProductWebShop.exception.OrderItemNotFoundExp;
import be.intecbrussel.ProductWebShop.model.Order;
import be.intecbrussel.ProductWebShop.model.OrderItem;
import be.intecbrussel.ProductWebShop.model.Product;
import be.intecbrussel.ProductWebShop.model.User;
import be.intecbrussel.ProductWebShop.repository.OrderItemRepository;
import be.intecbrussel.ProductWebShop.service.OrderItemService;
import be.intecbrussel.ProductWebShop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private OrderItemService orderItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddOrderItem() {
        // Arrange
        OrderItem orderItem = createSampleOrderItem();

        // Act
        orderItemService.addOrderItem(orderItem);

        Mockito.verify(orderItemRepository, Mockito.times(1)).save(orderItem);
    }

    @Test
    void testGetAllOrderItem() {
        // Arrange
        List<OrderItem> orderItemList = createSampleOrderItemList();
        when(orderItemRepository.findAll()).thenReturn(orderItemList);

        // Act
        List<OrderItem> result = orderItemService.getAllOrderItem();

        // Assert
        assertEquals(orderItemList, result);

        Mockito.verify(orderItemRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testGetOrderItemById() {
        // Arrange
        long orderItemId = 1;
        OrderItem orderItem = createSampleOrderItem();
        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(orderItem));

        // Act
        Optional<OrderItem> result = orderItemService.getOrderItemById(orderItemId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(orderItem, result.get());

        Mockito.verify(orderItemRepository, Mockito.times(1)).findById(orderItemId);
    }

    @Test
    void testGetOrderItemListByOrderId() {
        // Arrange
        long orderId = 1;
        List<OrderItem> orderItemList = createSampleOrderItemList();
        when(orderItemRepository.findOrderItemsByOrder_Id(orderId)).thenReturn(orderItemList);

        // Act
        List<OrderItem> result = orderItemService.getOrderItemListByOrderId(orderId);

        // Assert
        assertEquals(orderItemList, result);

        Mockito.verify(orderItemRepository, Mockito.times(1)).findOrderItemsByOrder_Id(orderId);
    }

    // niet werk
//    @Test
//    void testGetOrderItemByUserId() {
//        // Arrange
//        long userId = 1;
//        List<OrderItem> orderItemList = createSampleOrderItemList();
//        when(userService.getUserById(userId)).thenReturn(Optional.of(createSampleUser()));
//
//        // Act
//        List<OrderItem> result = orderItemService.getOrderItemByUserId(userId);
//
//        // Assert
//        assertEquals(orderItemList, result);
//    }

    @Test
    void testPatchProduct() {
        // Arrange
        OrderItem orderItem = createSampleOrderItem();
        when(orderItemRepository.save(orderItem)).thenReturn(orderItem);

        // Act
        Optional<OrderItem> result = orderItemService.patchProduct(orderItem);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(orderItem, result.get());

        Mockito.verify(orderItemRepository, Mockito.times(1)).save(orderItem);
    }

    @Test
    void testDeleteOrderItemById() {
        // Arrange
        long orderItemId = 1;
        when(orderItemRepository.existsById(orderItemId)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> orderItemService.deleteOrderItemById(orderItemId));

        Mockito.verify(orderItemRepository, Mockito.times(1)).existsById(orderItemId);
        Mockito.verify(orderItemRepository, Mockito.times(1)).deleteById(orderItemId);
    }

    @Test
    void testDeleteOrderItemById_OrderItemNotFound() {
        // Arrange
        long orderItemId = 1;
        when(orderItemRepository.existsById(orderItemId)).thenReturn(false);

        // Act & Assert
        assertThrows(OrderItemNotFoundExp.class, () -> orderItemService.deleteOrderItemById(orderItemId));


        Mockito.verify(orderItemRepository, Mockito.times(1)).existsById(orderItemId);
        Mockito.verify(orderItemRepository, Mockito.never()).deleteById(orderItemId);
    }

    // help method

    private OrderItem createSampleOrderItem() {
        OrderItem orderItem = new OrderItem(new Product("gg", "uu", "yy", 1, 2), 3);
        return orderItem;
    }

    private List<OrderItem> createSampleOrderItemList() {

        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(createSampleOrderItem());
        return orderItemList;
    }

    private User createSampleUser() {
        User user = new User("ttt", "ttt");
        return user;
    }
}
