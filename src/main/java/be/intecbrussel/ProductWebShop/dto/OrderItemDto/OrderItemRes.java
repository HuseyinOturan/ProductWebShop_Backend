package be.intecbrussel.ProductWebShop.dto.OrderItemDto;

import java.time.LocalDateTime;

public class OrderItemRes {

    private String productName;

    private double productPrice;

    private double quantity;

    public OrderItemRes(String productName, double productPrice, double quantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
