package be.intecbrussel.ProductWebShop.dto.OrderItemDto;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "OrderItemRes{" +
                "productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemRes that)) return false;
        return Double.compare(that.getProductPrice(), getProductPrice()) == 0 && Double.compare(that.getQuantity(), getQuantity()) == 0 && getProductName().equals(that.getProductName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductName(), getProductPrice(), getQuantity());
    }
}
