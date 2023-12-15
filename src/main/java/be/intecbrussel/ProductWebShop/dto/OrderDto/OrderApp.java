package be.intecbrussel.ProductWebShop.dto.OrderDto;

import java.util.List;

public class OrderApp {

    private Long id;
    private List<OrderItemApp> orderItemAppList;

    public OrderApp(Long id, List<OrderItemApp> orderItemAppList) {
        this.id = id;
        this.orderItemAppList = orderItemAppList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItemApp> getOrderItemAppList() {
        return orderItemAppList;
    }

    public void setOrderItemAppList(List<OrderItemApp> orderItemAppList) {
        this.orderItemAppList = orderItemAppList;
    }

    @Override
    public String toString() {
        return "OrderApp{" +
                "id=" + id +
                ", orderItemAppList=" + orderItemAppList +
                '}';
    }
}
