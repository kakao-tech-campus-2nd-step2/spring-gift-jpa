package gift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gift.controller.ProductController;
import gift.entity.Product;
import gift.service.ProductService;
import java.util.Arrays;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
        int page = 0;
        int size = 2;
        Page<Product> productsPage = new PageImpl<>(Arrays.asList(
            new Product(1L, "Product1", 100.0, "http://1.com"),
            new Product(2L, "Product2", 200.0, "http://2.com")
        ));
        when(productService.findAll(eq(page), eq(size))).thenReturn(productsPage);

        // Act
        ResponseEntity<Page<Product>> response = new ResponseEntity<>(
            productController.getAllProducts(page, size), HttpStatus.OK);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).getTotalElements());
        // Add further assertions for page content if needed
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
