package be.intecbrussel.ProductWebShop.Controller;

import be.intecbrussel.ProductWebShop.controller.AuthController;
import be.intecbrussel.ProductWebShop.controller.ProductController;
import be.intecbrussel.ProductWebShop.model.Product;
import be.intecbrussel.ProductWebShop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private AuthController authController;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddProduct_Success() {
        // Arrange
        Product product = createSampleProduct();
        when(productService.addProduct(product)).thenReturn(true);

        // Act
        ResponseEntity result = productController.addProduct(product);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testAddProduct_Failure() {
        // Arrange
        Product product = createSampleProduct();
        when(productService.addProduct(product)).thenReturn(false);

        // Act
        ResponseEntity result = productController.addProduct(product);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }


    @Test
    void testGetProductById_Success() {
        // Arrange
        long productId = 1;
        Optional<Product> expectedProduct = Optional.of(createSampleProduct());
        when(productService.getProductById(productId)).thenReturn(expectedProduct);

        // Act
        ResponseEntity<Optional<Product>> result = productController.getProductById(productId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedProduct, result.getBody());
    }

    @Test
    void testGetProductById_NotFound() {
        // Arrange
        long productId = 1;
        when(productService.getProductById(productId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Optional<Product>> result = productController.getProductById(productId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void testGetProductByName_Success() {
        // Arrange
        String productName = "SampleProduct";
        Optional<Product> expectedProduct = Optional.of(createSampleProduct());
        when(productService.getProductByName(productName)).thenReturn(expectedProduct);

        // Act
        ResponseEntity<Optional<Product>> result = productController.getProductByName(productName);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedProduct, result.getBody());
    }

    @Test
    void testGetProductByName_NotFound() {
        // Arrange
        String productName = "NonexistentProduct";
        when(productService.getProductByName(productName)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Optional<Product>> result = productController.getProductByName(productName);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    // Help
    private Product createSampleProduct() {
        Product product = new Product("aa", "aa", "aa", 1, 1);
        return product;
    }

    private List<Product> createSampleProductList() {

        List<Product> productList = new ArrayList<>();
        productList.add(createSampleProduct());
        return productList;
    }
}
