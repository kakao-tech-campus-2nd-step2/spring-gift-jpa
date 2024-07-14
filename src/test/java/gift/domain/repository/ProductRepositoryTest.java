package gift.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.domain.dto.request.ProductRequest;
import gift.domain.entity.Product;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProductRepositoryTest {

    private final ProductRepository productRepository;
    private Long id;
    private Product req1;
    private final Product req2;

    @Autowired
    ProductRepositoryTest(ProductRepository productRepository) {
        this.productRepository = productRepository;
        req2 = new Product("NewTestName", 2_000, "http://example.new.com");
    }

    @BeforeEach
    void setUp() {
        req1 = productRepository.save(new Product("TestName", 1_000, "http://example.com"));
        id = req1.getId();
    }

    @Test
    @DisplayName("ProductRepository.findAll() 테스트")
    void findAll() {
        List<Product> products = productRepository.findAll();
        assertThat(products.isEmpty()).isFalse();
        assertThat(products.stream().anyMatch(p -> p.equals(req1))).isTrue();
    }

    @Test
    @DisplayName("ProductRepository.findById() 테스트")
    void findById() {
        assertThat(productRepository.findById(id).isPresent()).isTrue();
    }

    @Test
    @DisplayName("ProductRepository.findByContents() 테스트")
    void findByContents() {
        assertThat(productRepository.findByContents(ProductRequest.of(req1)).isPresent()).isTrue();
    }

    @Test
    @DisplayName("ProductRepository.update() 테스트")
    void update() {
        Product product = productRepository.findById(id).get();
        product.set(ProductRequest.of(req2));
        product = productRepository.findById(id).get();

        assertThat(ProductRequest.of(product)).isEqualTo(ProductRequest.of(req2));
    }

    @Test
    @DisplayName("ProductRepository.save() 테스트")
    void save() {
        Product req = productRepository.save(req2);
        Product actual = productRepository.findById(req.getId()).get();

        assertThat(ProductRequest.of(actual)).isEqualTo(ProductRequest.of(req2));
    }

    @Test
    @DisplayName("ProductRepository.deleteById() 테스트")
    void deleteById() {
        productRepository.deleteById(id);
        assertTrue(productRepository.findById(id).isEmpty());
    }
}