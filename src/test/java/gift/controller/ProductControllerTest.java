package gift.controller;

import static org.junit.jupiter.api.Assertions.*;

import gift.dto.ProductDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
@AutoConfigureTestDatabase
class ProductControllerTest {
    @Autowired
    private ProductController productController;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS products");
        jdbcTemplate.execute("CREATE TABLE products (id LONG AUTO_INCREMENT, name VARCHAR(255), price NUMERIC, imageUrl VARCHAR(255))");
    }

    @Test
    void getAllProductTest() {
        jdbcTemplate.execute("INSERT INTO products (name, price) VALUES ('Product1', 100.0)");
        jdbcTemplate.execute("INSERT INTO products (name, price) VALUES ('Product2', 200.0)");

        List<ProductDto> result = productController.getAllProducts();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getProductByIdTest() {
        jdbcTemplate.execute("INSERT INTO products (name, price, imageUrl) VALUES ('Product1', 100.0, 'imageUrl')");
        ProductDto result = productController.getProductById(1L);
        assertNotNull(result);
        assertEquals("Product1", result.getName());
    }

    @Test
    void addProductTest() {
        ProductDto product = new ProductDto(null, "Product1", 100.0, "imageUrl");

        ProductDto result = productController.addProduct(product);
        assertNotNull(result);

        List<ProductDto> products = productController.getAllProducts();
        assertEquals(1, products.size());
    }

    @Test
    void updateProductTest() {
        jdbcTemplate.execute("INSERT INTO products (name, price, imageUrl) VALUES ('Product1', 100.0, 'imageUrl')");
        ProductDto product = new ProductDto(1L, "Updated Product", 150.0, "ImageUrl");

        ProductDto result = productController.updateProduct(product);
        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
    }

    @Test
    void deleteProduct() {
        jdbcTemplate.execute("INSERT INTO products (name, price, imageUrl) VALUES ('Product1', 100.0, 'imageUrl')");

        String result = productController.deleteProduct(1L);
        assertEquals("{status: success}", result);

        List<ProductDto> products = productController.getAllProducts();
        assertTrue(products.isEmpty());
    }
}

