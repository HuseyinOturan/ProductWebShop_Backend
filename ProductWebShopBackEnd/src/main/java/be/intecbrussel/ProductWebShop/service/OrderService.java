package be.intecbrussel.ProductWebShop.service;

import be.intecbrussel.ProductWebShop.exception.OrderNotFoundExp;
import be.intecbrussel.ProductWebShop.model.Order;
import be.intecbrussel.ProductWebShop.model.OrderItem;
import be.intecbrussel.ProductWebShop.model.Product;
import be.intecbrussel.ProductWebShop.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    // properties
    private OrderRepository orderRepository;
    private ProductService productService;

    // constructors
    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService=productService;
    }
    // custom methods

    // create
  public boolean addOrder(Order order){

        try {
            for(OrderItem orderItem : order.getOrderItems()){
                Double stock=orderItem.getProduct().getStock();
                Double quantitiy= orderItem.getQuantity();

                if (quantitiy>stock){
                    return false;
                }

            }
            orderRepository.save(order);
            for (OrderItem orderItem: order.getOrderItems()){
               orderItem.getProduct().setStock(-orderItem.getQuantity());

                productService.patchProduct(orderItem.getProduct());
            }

            return true;
        } catch (Exception e){

            e.printStackTrace();
            return false;
        }
  }
  // read
    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }
    public Optional<Order> getOrderById(Long id){
        return orderRepository.findById(id);
    }

    public List<Order> getOrderByUserId(Long userId){
        return orderRepository.findOrdersByUser_Id(userId);


    }
    // delete
    public void deleteOrderByid(Long id) throws OrderNotFoundExp{
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new OrderNotFoundExp("Order not found with id: "+ id);
        }
    }
}
