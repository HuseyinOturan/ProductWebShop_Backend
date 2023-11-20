package be.intecbrussel.ProductWebShop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "customer_order")
public class Order {
    // properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "`user_id`")
    private User user;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems;
    private LocalDateTime localDateTime;
    private double totalPrice;

    // constructors

    protected Order(){}


    public Order(long id, User user, List<OrderItem> orderItems, LocalDateTime localDateTime, double totalPrice) {
        this.id = id;
        this.user = user;
        this.orderItems = orderItems;
        this.localDateTime = localDateTime;
        this.totalPrice=calculateTotalPrice();
    }

    // getters and setters


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    private double calculateTotalPrice() {
        double totalPrice = 0.0;

        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getOrderItemPrice();
        }

        return totalPrice;
    }
}
