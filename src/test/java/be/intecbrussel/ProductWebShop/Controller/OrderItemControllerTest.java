package be.intecbrussel.ProductWebShop.Controller;

import be.intecbrussel.ProductWebShop.controller.OrderItemController;
import be.intecbrussel.ProductWebShop.dto.OrderItemDto.OrderItemRes;
import be.intecbrussel.ProductWebShop.model.OrderItem;
import be.intecbrussel.ProductWebShop.model.Product;
import be.intecbrussel.ProductWebShop.service.OrderItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class OrderItemControllerTest {

    @Mock
    private OrderItemService orderItemService;

    @InjectMocks
    private OrderItemController orderItemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllOrderItem_Success() {
        // Arrange
        List<OrderItem> orderItemList = createSampleOrderItemList();
        when(orderItemService.getAllOrderItem()).thenReturn(orderItemList);

        // Act
        ResponseEntity result = orderItemController.getAllOrderItem();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(orderItemList, result.getBody());
    }

    @Test
    void testGetAllOrderItem_Exception() {
        // Arrange
        when(orderItemService.getAllOrderItem()).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity result = orderItemController.getAllOrderItem();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testGetOrderItemByUserId_Success() {
        // Arrange
        long userId = 1;
        List<OrderItemRes> expectedOrderItemResList = createSampleOrderItemResList();
        when(orderItemService.getOrderItemByUserId(userId)).thenReturn(createSampleOrderItemList());

        // Act
        ResponseEntity<List<OrderItemRes>> result = orderItemController.getOrderItemByUserId(userId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedOrderItemResList, result.getBody());
    }

    @Test
    void testGetOrderItemByUserId_Exception() {
        // Arrange
        long userId = 1;
        when(orderItemService.getOrderItemByUserId(userId)).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity<List<OrderItemRes>> result = orderItemController.getOrderItemByUserId(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    // HELP METHOD

    private OrderItem createSampleOrderItem() {
        OrderItem orderItem = new OrderItem(new Product("product", "uu", "yy", 12, 12), 12);
        return orderItem;
    }

    private List<OrderItem> createSampleOrderItemList() {
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(createSampleOrderItem());
        return orderItemList;
    }

    private List<OrderItemRes> createSampleOrderItemResList() {

        List<OrderItemRes> orderItemResList = new ArrayList<>();
        OrderItemRes orderItemRes = new OrderItemRes("product", 12, 12);
        orderItemResList.add(orderItemRes);
        return orderItemResList;
    }


}
