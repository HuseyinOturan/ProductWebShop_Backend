package be.intecbrussel.ProductWebShop.dto.OrderDto;

public class OrderItemApp {

    private long productAppId;
    private double amount;

    public OrderItemApp(long productAppId, double amount) {
        this.productAppId = productAppId;
        this.amount = amount;
    }

    public long getProductAppId() {
        return productAppId;
    }

    public void setProductAppId(long productAppId) {
        this.productAppId = productAppId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderItemApp{" +
                "productAppId=" + productAppId +
                ", amount=" + amount +
                '}';
    }
}
