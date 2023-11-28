package be.intecbrussel.ProductWebShop.dto;

public class OrderItemRequest {

    private Long productId;
    private Double quantity;

    public OrderItemRequest(Long productId, Double quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantitiy(Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItemRequest{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
