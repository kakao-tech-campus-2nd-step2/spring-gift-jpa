package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.model.Product;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 저장 및 ID로 조회")
    public void testSaveAndFindById() {
        Product product = new Product(null, "Test Product", 100, "test.jpg");
        Product savedProduct = productRepository.save(product);
        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());

        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("Test Product");
    }

    @Test
    @DisplayName("모든 상품 조회")
    public void testFindAll() {
        long initialCount = productRepository.count();

        Product product1 = new Product(null, "Product 1", 100, "prod1.jpg");
        Product product2 = new Product(null, "Product 2", 200, "prod2.jpg");

        productRepository.save(product1);
        productRepository.save(product2);

        Iterable<Product> products = productRepository.findAll();
        assertThat(products).hasSize((int) initialCount + 2);
    }

    @Test
    @DisplayName("상품 삭제")
    public void testDelete() {
        Product product = new Product(null, "Test Product", 100, "test.jpg");
        Product savedProduct = productRepository.save(product);

        productRepository.deleteById(savedProduct.getId());
        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());

        assertThat(foundProduct).isNotPresent();
    }
}
