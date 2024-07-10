package gift.repository;

import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import gift.entity.Product;
import jakarta.transaction.Transactional;

@DataJpaTest
public class ProductRepositoryTest {
    
    @Autowired
    private ProductRepository productRepository;

    private Product expected;
    private Product actual;
    private long notExistId;

    @BeforeEach
    void setUp(){
        expected = new Product("testName", 1, "testUrl");
        actual = productRepository.save(expected);
        notExistId = 1000L;
    }

    @Transactional
    @Test
    void save() {
    
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Transactional
    @Test
    void findById() {

        Optional<Product> product = productRepository.findById(expected.getId());
        Optional<Product> notExistProduct = productRepository.findById(notExistId);

        assertAll(
            () -> assertThat(product).isPresent(),
            () -> assertThat(product.get().getId()).isEqualTo(expected.getId()),
            () -> assertThat(notExistProduct).isNotPresent()
        );
    }

    @Transactional
    @Test
    void update() {

        String updateName = "update name";

        Product product = productRepository.findById(expected.getId()).orElseThrow();
        product.setName(updateName);
        productRepository.save(product);

        Optional<Product> updatedProduct = productRepository.findById(expected.getId());

        assertAll(
            () -> assertThat(updatedProduct).isPresent(),
            () -> assertThat(updatedProduct.get().getName()).isEqualTo(updateName)
        );
    }

    @Transactional
    @Test
    void delete(){
        
        productRepository.delete(expected);

        Optional<Product> product = productRepository.findById(expected.getId());
        assertThat(product).isNotPresent();
    }

}
