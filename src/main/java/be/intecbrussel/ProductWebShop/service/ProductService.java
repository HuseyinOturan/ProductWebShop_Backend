package be.intecbrussel.ProductWebShop.service;

import be.intecbrussel.ProductWebShop.exception.ProductNotFoundExp;
import be.intecbrussel.ProductWebShop.model.Product;
import be.intecbrussel.ProductWebShop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    // properties
    private ProductRepository productRepository;

    // constructors
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //customs methods

    // create
    public boolean addProduct(Product product) {
        if (product.getName().isEmpty() || product.getImg().isEmpty() || product.getDescriptions().isEmpty()) {
            return false;
        }
        if (product.getStock() <= 0 || product.getPrice() <= 0) {
            return false;
        }

        productRepository.save(product);
        return true;
    }

    // read
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> getProductByName(String productName) {
        return productRepository.findByName(productName);
    }


    // update
    public Optional<Product> patchProduct(Product product) {

        return Optional.of(productRepository.save(product));
    }

    // delete
    public void deleteProductById(Long id) throws ProductNotFoundExp {

        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new ProductNotFoundExp("Product not found is : " + id);
        }
    }
}
