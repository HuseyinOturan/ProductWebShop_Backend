package be.intecbrussel.ProductWebShop.dto;

public class OrderItemFront {

    private String productName;
    private double amount;

    public OrderItemFront(String productName, double amonut) {
        this.productName = productName;
        this.amount = amonut;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getAmonut() {
        return amount;
    }

    public void setAmonut(double amonut) {
        this.amount = amonut;
    }

    @Override
    public String toString() {
        return "OrderFront{" +
                "productName='" + productName + '\'' +
                ", amonut=" + amount +
                '}';
    }
}
