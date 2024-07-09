package gift.repository;

import gift.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void saveTest() {
        // Given
        Product product = new Product("아몬드", 500, "image.jpg");
        Long savedProductId = productRepository.save(product).getId();

        // When
        Optional<Product> foundProduct = productRepository.findById(savedProductId);

        // Then
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("아몬드");
    }

    @Test
    void readTest() {
        // Given
        Product product1 = new Product("아몬드", 500, "image.jpg");
        Product product2 = new Product("초코", 5400, "image2.jpg");
        Long savedProductId1 = productRepository.save(product1).getId();
        Long savedProductId2 = productRepository.save(product2).getId();
        Set<Product> savedProducts = new HashSet<>();
        savedProducts.add(product1);
        savedProducts.add(product2);

        // When
        List<Product> foundProducts = productRepository.findAll();

        // Then
        assertThat(foundProducts.size()).isEqualTo(2);
        foundProducts.stream()
                .forEach((product -> {
                    assertThat(product).isIn(savedProducts);
                }));
    }

    @Test
    void updateTest() throws Exception {
        // Given
        Product product = new Product("아몬드", 500, "image.jpg");
        Long savedProductId = productRepository.save(product).getId();

        // When
        Optional<Product> foundProduct = productRepository.findById(savedProductId);
        foundProduct.get().setName("아몬드봉봉");
        productRepository.save(foundProduct.get());

        // Then
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("아몬드봉봉");
    }

    @Test
    void deleteTest() throws Exception {
        // Given
        Product product = new Product("아몬드", 500, "image.jpg");
        Long savedProductId = productRepository.save(product).getId();

        // When
        Optional<Product> foundProduct = productRepository.findById(savedProductId);
        productRepository.delete(foundProduct.get());
        foundProduct = productRepository.findById(savedProductId);

        // Then
        assertThat(foundProduct).isEmpty();
    }

}
