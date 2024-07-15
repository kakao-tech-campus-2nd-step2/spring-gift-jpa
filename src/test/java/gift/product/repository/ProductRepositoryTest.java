package gift.product.repository;

import gift.product.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void saveTest() {
        // Given
        Product product = new Product("Product 1", 10, "http://www.google.com");

        // When
        product = productRepository.save(product);

        // Then
        Optional<Product> foundProduct = productRepository.findById(product.product_id());
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().name()).isEqualTo("Product 1");
    }

    @Test
    public void findByIdTest() {
        // Given
        Product product = new Product("Product 2", 20, "http://www.google.com");
        product = productRepository.save(product);

        // When
        Optional<Product> foundProduct = productRepository.findById(product.product_id());

        // Then
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().name()).isEqualTo("Product 2");
    }

    @Test
    public void deleteTest() {
        // Given
        Product product = new Product("Product 3", 30, "http://www.google.com");
        product = productRepository.save(product);

        // When
        productRepository.deleteById(product.product_id());

        // Then
        Optional<Product> deletedProduct = productRepository.findById(product.product_id());
        assertThat(deletedProduct).isEmpty();
    }

    @Test
    public void findAllTest() {
        // Given
        Product product1 = new Product("Product 4", 40, "http://www.google.com");
        Product product2 = new Product("Product 5", 50, "http://www.google.com");
        productRepository.save(product1);
        productRepository.save(product2);

        // When
        Iterable<Product> products = productRepository.findAll();

        // Then
        assertThat(products).hasSize(2);
        assertThat(products).extracting(Product::name).contains("Product 4", "Product 5");
    }

    @Test
    public void updateTest() {
        // Given
        Product product = new Product("Product 6", 60, "http://www.google.com");
        product = productRepository.save(product);

        // When
        product.update("Product 6", 80, "http://www.google.com");
        product = productRepository.save(product);

        // Then
        Optional<Product> updatedProduct = productRepository.findById(product.product_id());
        assertThat(updatedProduct).isPresent();
        assertThat(updatedProduct.get().name()).isEqualTo("Updated Product 6");
        assertThat(updatedProduct.get().getPrice()).isEqualTo(70);
        assertThat(updatedProduct.get().getImgUrl()).isEqualTo("http://www.google.com");
    }
}