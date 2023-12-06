package be.intecbrussel.ProductWebShop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;

@Entity
public class OrderItem {

    // properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference("product-orderitem")
    private Product product;
    private double quantity;
    @ManyToOne
    @JoinColumn(name = "customer_order_id")
    @JsonBackReference("order-orderitem")
    private Order order;

    // constructors
    public OrderItem(Product product, double quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public OrderItem(Product product, double quantity, Order order) {
        this.product = product;
        this.quantity = quantity;
        this.order = order;
    }

    protected OrderItem() {
    }

    // getters and setters
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", order=" + order +
                '}';
    }
}
