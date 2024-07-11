package gift;

import gift.domain.model.entity.Product;
import gift.domain.repository.ProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void saveProductTest() {
        // given
        Product product = new Product("Test Product", 1000L, "http://example.com/image.jpg");

        // when
        Product savedProduct = productRepository.save(product);

        // then
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Test Product");
        assertThat(savedProduct.getPrice()).isEqualTo(1000L);
        assertThat(savedProduct.getImageUrl()).isEqualTo("http://example.com/image.jpg");
    }

    @Test
    public void findByIdTest() {
        // given
        Product product = new Product("Test Product", 1000L, "http://example.com/image.jpg");
        productRepository.save(product);

        // when
        Product found = productRepository.findById(product.getId()).orElse(null);

        // then
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo(product.getName());
        assertThat(found.getPrice()).isEqualTo(product.getPrice());
        assertThat(found.getImageUrl()).isEqualTo(product.getImageUrl());
    }

    @Test
    public void updateProductTest() {
        // given
        Product product = new Product("Test Product", 1000L, "http://example.com/image.jpg");
        productRepository.save(product);

        // when
        Product savedProduct = productRepository.findById(product.getId()).orElse(null);
        assertThat(savedProduct).isNotNull();
        savedProduct.update("Updated Product", 2000L, "http://example.com/updated.jpg");
        productRepository.save(savedProduct);

        // then
        Product updatedProduct = productRepository.findById(product.getId()).orElse(null);
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getName()).isEqualTo("Updated Product");
        assertThat(updatedProduct.getPrice()).isEqualTo(2000L);
        assertThat(updatedProduct.getImageUrl()).isEqualTo("http://example.com/updated.jpg");
    }

    @Test
    public void existsByNameTest() {
        // given
        String productName = "Test Product";
        Product product = new Product(productName, 1000L, "http://example.com/image.jpg");
        productRepository.save(product);

        // when
        boolean existsProduct = productRepository.existsByName(productName);
        boolean notExistsProduct = productRepository.existsByName("Non-existent Product");

        // then
        assertThat(existsProduct).isTrue();
        assertThat(notExistsProduct).isFalse();
    }

    @Test
    public void findByNameTest() {
        // given
        String productName = "Test Product";
        Product product = new Product(productName, 1000L, "http://example.com/image.jpg");
        productRepository.save(product);

        // when
        Optional<Product> found = productRepository.findByName(productName);

        // then
        assertThat(found).isPresent();
        found.ifPresent(foundProduct -> {
            assertThat(foundProduct.getName()).isEqualTo(product.getName());
            assertThat(foundProduct.getPrice()).isEqualTo(product.getPrice());
            assertThat(foundProduct.getImageUrl()).isEqualTo(product.getImageUrl());
        });
    }
}