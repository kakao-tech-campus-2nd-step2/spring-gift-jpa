package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import gift.exception.product.ProductNotFoundException;
import gift.model.Product;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ProductService productService;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS products CASCADE");
        jdbcTemplate.execute("CREATE TABLE products ("
            + "id LONG,"
            + " name VARCHAR(255),"
            + " price INT,"
            + " imageUrl VARCHAR(255),"
            + " PRIMARY KEY (id))"
        );
    }

    @Test
    @DisplayName("getAllProducts empty test")
    void getAllProductsEmptyTest() {
        //when
        List<Product> products = productService.getAllProducts();

        //then
        assertThat(products).isEmpty();
    }

    @Test
    @DisplayName("getAllProducts test")
    void getAllProductsTest() {
        //given
        Product product1 = new Product(1L, "product1", 10000, null);
        Product product2 = new Product(2L, "product2", 20000, null);
        productService.addProduct(product1);
        productService.addProduct(product2);
        List<Product> expected = List.of(product1, product2);

        //when
        List<Product> products = productService.getAllProducts();

        //then
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(2);
        assertThat(products).containsAll(expected);
    }

    @Test
    @DisplayName("getProductById exception test")
    void getProductByIdExceptionTest() {
        //given
        Product product1 = new Product(1L, "product1", 10000, null);
        Product product2 = new Product(2L, "product2", 20000, null);
        productService.addProduct(product1);
        productService.addProduct(product2);

        //when & then
        assertThatThrownBy(() -> productService.getProductById(3L))
            .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("getProductById test")
    void getProductByIdTest() {
        //given
        Product product1 = new Product(1L, "product1", 10000, null);
        Product product2 = new Product(2L, "product2", 20000, null);
        productService.addProduct(product1);
        productService.addProduct(product2);

        //when
        Product product = productService.getProductById(2L);

        //then
        assertThat(product).isEqualTo(product2);
    }

    @Test
    @DisplayName("updateProduct test")
    void updateProductTest() {
        //given
        Product product1 = new Product(1L, "product1", 10000, null);
        Product product2 = new Product(2L, "product2", 20000, null);
        Product newProduct = new Product(1L, "product3", 30000, null);

        productService.addProduct(product1);
        productService.addProduct(product2);


        //when
        productService.updateProduct(1L, newProduct);
        Product updatedProduct = productService.getProductById(1L);

        //then
        assertThat(updatedProduct).isEqualTo(newProduct);
    }
    
    @Test
    @DisplayName("deleteProduct test")
    void deleteProductTest() {
        //given
        Product product1 = new Product(1L, "product1", 10000, null);
        Product product2 = new Product(2L, "product2", 20000, null);

        productService.addProduct(product1);
        productService.addProduct(product2);

        //when
        productService.deleteProduct(1L);
        List<Product> deletedProducts = productService.getAllProducts();

        //then
        assertThat(deletedProducts).hasSize(1);
        assertThat(deletedProducts).doesNotContain(product1);
        assertThat(deletedProducts).contains(product2);
    }

    @Test
    @DisplayName("deleteProduct exception test")
    void deleteProductExceptionTest() {
        //given
        Product product1 = new Product(1L, "product1", 10000, null);
        Product product2 = new Product(2L, "product2", 20000, null);

        productService.addProduct(product1);
        productService.addProduct(product2);

        //when & then
        assertThatThrownBy(() -> productService.getProductById(3L))
            .isInstanceOf(ProductNotFoundException.class);
    }
}
