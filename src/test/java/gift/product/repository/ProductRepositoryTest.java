package gift.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.product.model.Product;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@Import(ProductRepository.class)
class ProductRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS product");
        jdbcTemplate.execute("CREATE TABLE product (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), price INT, imgUrl VARCHAR(255))");
        jdbcTemplate.execute("INSERT INTO product (name, price, imgUrl) VALUES ('아이스 카페 아메리카노 T', 4500, 'https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg')");
    }

    @Test
    void testFindById() {
        Optional<Product> product = productRepository.findById(1L);
        assertThat(product).isPresent();
        assertThat(product.get().getName()).isEqualTo("아이스 카페 아메리카노 T");
    }

    @Test
    void testFindAll() {
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1);
    }

    @Test
    void testSaveNewProduct () {
        Product newProduct = new Product();
        newProduct.setName("New Product");
        newProduct.setPrice(4500);
        newProduct.setImgUrl("http://example.com/newProduct.png");

        Product savedProduct = productRepository.save(newProduct);
        assertThat(savedProduct.getId()).isNotNull();
        Optional<Product> product = productRepository.findById(savedProduct.getId());
        assertThat(product).isPresent();
        assertThat(savedProduct.getName()).isEqualTo("New Product");
    }

    @Test
    void testSaveUpdateProduct() {
        Product updatedProduct = productRepository.findById(1L).get();
        updatedProduct.setName("Updated Product");
        updatedProduct.setPrice(5000);

        productRepository.save(updatedProduct);
        Optional<Product> product = productRepository.findById(1L);
        assertThat(product).isPresent();
        assertThat(product.get().getName()).isEqualTo("Updated Product");
    }

    @Test
    void testDeleteById() {
        productRepository.deleteById(1L);
        Optional<Product> product = productRepository.findById(1L);
        assertThat(product).isNotPresent();
    }
}