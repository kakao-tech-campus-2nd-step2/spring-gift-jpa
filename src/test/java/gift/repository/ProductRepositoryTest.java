package gift.repository;

import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import gift.entity.Product;

@DataJpaTest
public class ProductRepositoryTest {
    
    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        Product expected = new Product("testName", 1, "testUrl");
        Product actual = productRepository.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findById() {

        Product expected = new Product("testName", 1, "testUrl");
        productRepository.save(expected);

        long productId = 1L;
        Optional<Product> product = productRepository.findById(productId);

        assertAll(
            () -> assertThat(product).isPresent(),
            () -> assertThat(product.get().getId()).isEqualTo(productId)
        );
    }

    @Test
    void update() {

        Product expected = new Product("testName", 1, "testUrl");
        productRepository.save(expected);

        String updateName = "update name";

        long productId = 1L;
        Product product = productRepository.findById(productId).get();
        product.setName(updateName);
        productRepository.save(product);

        Optional<Product> updatedProduct = productRepository.findById(productId);

        assertAll(
            () -> assertThat(updatedProduct).isPresent(),
            () -> assertThat(updatedProduct.get().getId()).isEqualTo(productId)
        );

    }

    @Test
    void delete(){

        Product expected = new Product("testName", 1, "testUrl");
        productRepository.save(expected);
        
        long productId = 1L;
        productRepository.deleteById(productId);

        Optional<Product> product = productRepository.findById(productId);
        assertThat(product).isNotPresent();
    }

}
