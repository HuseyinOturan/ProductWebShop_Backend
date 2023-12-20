package be.intecbrussel.ProductWebShop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @JsonManagedReference("order-orderitem")
    private List<OrderItem> orderItemList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("order-AuthUser")
    private AuthUser authUser;
    private LocalDateTime localDateTime;

    // constructors
    protected Order() {
    }

    public Order(AuthUser authUser, List<OrderItem> orderItems) {
        this.authUser = authUser;
        this.orderItemList = orderItems;
        this.localDateTime = LocalDateTime.now();
    }

    // getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public AuthUser getUser() {
        return authUser;
    }

    public void setUser(AuthUser authUser) {
        this.authUser = authUser;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderItemList=" + orderItemList +
                ", user=" + authUser +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
