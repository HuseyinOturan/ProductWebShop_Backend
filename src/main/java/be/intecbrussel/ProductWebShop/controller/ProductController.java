package be.intecbrussel.ProductWebShop.controller;

import be.intecbrussel.ProductWebShop.exception.ProductNotFoundExp;
import be.intecbrussel.ProductWebShop.model.Product;
import be.intecbrussel.ProductWebShop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    // properties
    private ProductService productService;

    // constructors
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // getters and setters
    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    // custom methods
    // create
    @PostMapping("/addProduct")
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

    // read
    @GetMapping("/getAllProduct")
    public ResponseEntity getAllProduct() {
        try {
            return ResponseEntity.ok(productService.getAllProduct());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getProductById")
    public ResponseEntity<Optional<Product>> getProductById(@RequestParam long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product); // return product and status HTTP 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Return HTTP 404 Not Found
        }
    }

    @GetMapping("/getProductByName")
    public ResponseEntity<Optional<Product>> getProductByName(@RequestParam String productName) {

        Optional<Product> product = productService.getProductByName(productName);
        if (product.isPresent()) {
            return ResponseEntity.ok(product); // return product and status HTTP 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Return HTTP 404 Not Found
        }

    }

    // update
    @PatchMapping("/patchProduct")
    public ResponseEntity patchProduct(@RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.patchProduct(product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // delete
    @DeleteMapping("deleteProductById")
    public ResponseEntity<String> deleteProductById(@RequestParam Long id) {

        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok("Product deleted");
        } catch (ProductNotFoundExp e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
