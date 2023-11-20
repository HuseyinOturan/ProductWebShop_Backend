package be.intecbrussel.ProductWebShop.model;

import jakarta.persistence.*;

@Entity
public class OrderItem {

        // properties
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "product_id")
        private Product product;
        private double quantity;
        private double orderItemPrice;

        // constructors
        public OrderItem(Long id, Product product, double quantity) {
                this.id = id;
                this.product = product;
                this.quantity = quantity;
                this.orderItemPrice=product.getPrice() * quantity;
        }

        protected OrderItem(){}

        // getters and setters
        public Long getId() {
                return id;
        }

        public void setId(Long id) {
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

        public double getOrderItemPrice() {
                return orderItemPrice;
        }

        public void setOrderItemPrice(double orderItemPrice) {
                this.orderItemPrice = orderItemPrice;
        }
}


