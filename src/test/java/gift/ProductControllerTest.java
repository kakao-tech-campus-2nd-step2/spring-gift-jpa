package gift;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;

import gift.entity.Product;
import gift.service.ProductService;
import gift.controller.ProductController;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class ProductControllerTest {

    private ProductService productService;
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
    }

    @Test
    public void testGetAllProducts() {
        // Arrange
        List<Product> products = Arrays.asList(
            new Product(1L, "Product1", 100.0, "http://1.com"),
            new Product(2L, "Product2", 200.0, "http://2.com")
        );
        when(productService.findAll()).thenReturn(products);

        // Act
        List<Product> result = productController.getAllProducts();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Product1", result.get(0).getName());
        assertEquals("Product2", result.get(1).getName());
    }

    @Test
    public void testGetProductById() {
        // Arrange
        Product product = new Product(1L, "Product1", 100.0, "http://1.com");
        when(productService.findById(1L)).thenReturn(product);

        // Act
        ResponseEntity<Product> response = productController.getProductById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product1", response.getBody().getName());
    }

    @Test
    public void testCreateProduct() {
        // Arrange
        Product product = new Product(null, "Product1", 100.0, "http://1.com");
        Product savedProduct = new Product(1L, "Product1", 100.0, "http://1.com");
        when(productService.save(any(Product.class))).thenReturn(savedProduct);

        // Act
        ResponseEntity<Product> response = productController.createProduct(product);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Product1", response.getBody().getName());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testUpdateProduct() {
        // Arrange
        Product product = new Product(1L, "Updated Product", 150.0, "http://12.com");
        when(productService.update(eq(1L), any(Product.class))).thenReturn(product);

        // Act
        ResponseEntity<Product> response = productController.updateProduct(1L, product);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Product", response.getBody().getName());
        assertEquals(150.0, response.getBody().getPrice());
    }

    @Test
    public void testDeleteProduct() {
        // Arrange

        // Act
        productController.deleteProduct(1L);

        // Assert
    }
}
