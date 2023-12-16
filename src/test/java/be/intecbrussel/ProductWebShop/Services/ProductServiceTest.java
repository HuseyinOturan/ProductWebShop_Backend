package be.intecbrussel.ProductWebShop.Services;

import be.intecbrussel.ProductWebShop.exception.ProductNotFoundExp;
import be.intecbrussel.ProductWebShop.model.Product;
import be.intecbrussel.ProductWebShop.repository.ProductRepository;
import be.intecbrussel.ProductWebShop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddProduct_Success() {
        // Arrange
        Product newProduct = new Product("Product1", "image.jpg", "Description", 10, 29.99);

        // Mocking
        when(productRepository.save(newProduct)).thenReturn(newProduct);

        // Act
        boolean result = productService.addProduct(newProduct);

        // Assert
        assertTrue(result);


        Mockito.verify(productRepository, Mockito.times(1)).save(newProduct);
    }

    @Test
    void testAddProduct_Failure_InvalidProduct() {
        // Arrange
        Product invalidProduct = new Product("", "", "", -1, -1);

        // Act
        boolean result = productService.addProduct(invalidProduct);

        // Assert
        assertFalse(result);

    }

    @Test
    void testGetAllProduct() {
        // Arrange
        List<Product> productList = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(productList);

        // Act
        List<Product> result = productService.getAllProduct();

        // Assert
        assertEquals(productList, result);


        Mockito.verify(productRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testGetProductById_Success() {
        // Arrange
        long productId = 1;
        Product product = new Product("Product1", "image.jpg", "Description", 10, 29.99);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Optional<Product> result = productService.getProductById(productId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(product, result.get());


        Mockito.verify(productRepository, Mockito.times(1)).findById(productId);
    }

    @Test
    void testGetProductById_Failure_ProductNotFound() {
        // Arrange
        long productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundExp.class, () -> productService.deleteProductById(productId));

        // Verify that the findById method was called
        Mockito.verify(productRepository, Mockito.times(0)).findById(productId);
    }

    @Test
    void testDeleteProductById_Success() {
        // Arrange
        long productId = 1;
        when(productRepository.existsById(productId)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> productService.deleteProductById(productId));


        Mockito.verify(productRepository, Mockito.times(1)).existsById(productId);
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(productId);
    }

    @Test
    void testDeleteProductById_Failure_ProductNotFound() {
        // Arrange
        long productId = 1;
        when(productRepository.existsById(productId)).thenReturn(false);

        // Act & Assert
        assertThrows(ProductNotFoundExp.class, () -> productService.deleteProductById(productId));

        Mockito.verify(productRepository, Mockito.times(1)).existsById(productId);
        Mockito.verify(productRepository, Mockito.never()).deleteById(productId);
    }
}
