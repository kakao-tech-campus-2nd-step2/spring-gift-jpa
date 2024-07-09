// ProductRepositoryTest.java
package gift.repository;

import gift.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        Product expected = new Product("Test Product", 100, "http://example.com/test.jpg");
        Product actual = productRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
                () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    void findById() {
        Product savedProduct = productRepository.save(new Product("Test Product", 100, "http://example.com/test.jpg"));
        Optional<Product> actual = productRepository.findById(savedProduct.getId());
        assertThat(actual).isPresent();
        actual.ifPresent(product -> assertThat(product.getName()).isEqualTo("Test Product"));
    }

    @Test
    void findAll() {
        productRepository.save(new Product("Product 1", 100, "http://example.com/1.jpg"));
        productRepository.save(new Product("Product 2", 200, "http://example.com/2.jpg"));
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(2);
    }

    @Test
    void deleteById() {
        Product savedProduct = productRepository.save(new Product("Product", 100, "http://example.com/delete.jpg"));
        productRepository.deleteById(savedProduct.getId());
        Optional<Product> deletedProduct = productRepository.findById(savedProduct.getId());
        assertThat(deletedProduct).isNotPresent();
    }
}
