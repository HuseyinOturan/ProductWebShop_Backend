package be.intecbrussel.ProductWebShop.dto;

import java.util.List;

public class OrderFront {

    private String userEmail;

    private List<OrderItemFront> orderItemFrontList;

    public OrderFront(String userEmail, List<OrderItemFront> orderItemFrontList) {
        this.userEmail = userEmail;
        this.orderItemFrontList = orderItemFrontList;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<OrderItemFront> getOrderItemFrontList() {
        return orderItemFrontList;
    }

    public void setOrderItemFrontList(List<OrderItemFront> orderItemFrontList) {
        this.orderItemFrontList = orderItemFrontList;
    }

    @Override
    public String toString() {
        return "OrderFront{" +
                "userEmail='" + userEmail + '\'' +
                ", orderItemFrontList=" + orderItemFrontList +
                '}';
    }
}
