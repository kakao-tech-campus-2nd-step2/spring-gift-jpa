package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import gift.dto.product.AddProductRequest;
import gift.dto.product.UpdateProductRequest;
import gift.exception.product.ProductNotFoundException;
import gift.entity.Product;
import gift.util.mapper.ProductMapper;
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
    void setupTable() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS products CASCADE");
        jdbcTemplate.execute("CREATE TABLE products ("
            + "id LONG,"
            + " name VARCHAR(255),"
            + " price INT,"
            + " imageUrl VARCHAR(255),"
            + " PRIMARY KEY (id))"
        );
    }

    void setupInsertion() {
        var product1 = new AddProductRequest(1L, "product1", 10000, null);
        var product2 = new AddProductRequest(2L, "product2", 20000, null);

        productService.addProduct(product1);
        productService.addProduct(product2);
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
        setupInsertion();
        Product product1 = new Product(1L, "product1", 10000, null);
        Product product2 = new Product(2L, "product2", 20000, null);
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
        setupInsertion();

        //when & then
        assertThatThrownBy(() -> productService.getProductById(3L))
            .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("getProductById test")
    void getProductByIdTest() {
        //given
        setupInsertion();
        Product product2 = new Product(2L, "product2", 20000, null);

        //when
        Product product = productService.getProductById(2L);

        //then
        assertThat(product).isEqualTo(product2);
    }

    @Test
    @DisplayName("updateProduct test")
    void updateProductTest() {
        //given
        setupInsertion();
        UpdateProductRequest request = new UpdateProductRequest("product3", 30000, null);

        //when
        productService.updateProduct(1L, request);
        Product actual = productService.getProductById(1L);
        Product expected = ProductMapper.toProduct(1L, request);

        //then
        assertThat(actual).isEqualTo(expected);
    }
    
    @Test
    @DisplayName("deleteProduct test")
    void deleteProductTest() {
        //given
        setupInsertion();
        Product product1 = new Product(1L, "product1", 10000, null);
        Product product2 = new Product(2L, "product2", 20000, null);

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
        setupInsertion();

        //when & then
        assertThatThrownBy(() -> productService.getProductById(3L))
            .isInstanceOf(ProductNotFoundException.class);
    }
}
