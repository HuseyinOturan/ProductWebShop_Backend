package be.intecbrussel.ProductWebShop.dto.OrderDto;

import java.util.List;

public class OrderRequest {
    private Long userId;
    private List<OrderItemRequest> orderItemRequestList;

    public OrderRequest(Long userId, List<OrderItemRequest> orderItemRequestList) {
        this.userId = userId;
        this.orderItemRequestList = orderItemRequestList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItemRequest> getOrderItemRequestList() {
        return orderItemRequestList;
    }

    public void setOrderItemRequestList(List<OrderItemRequest> orderItemRequestList) {
        this.orderItemRequestList = orderItemRequestList;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "userId=" + userId +
                ", orderItemRequestList=" + orderItemRequestList +
                '}';
    }
}
