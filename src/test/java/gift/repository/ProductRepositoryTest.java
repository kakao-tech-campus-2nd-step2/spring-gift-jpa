package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.domain.Product;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("Create Test")
    void save() {
        // given
        Product expected = new Product("test",100,"http://example");

        // when
        Product actual = productRepository.save(expected);

        // then
        assertAll(
            ()->assertThat(actual.getId()).isNotNull(),
            ()->assertThat(actual.getName()).isEqualTo(expected.getName()),
            ()->assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            ()->assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    @DisplayName("Read By Id Test")
    void findById() {
        // given
        String expectedName = "test";
        int expectedPrice = 100;
        String expectedImageUrl = "http://example";
        Product expected = new Product(expectedName, expectedPrice, expectedImageUrl);
        Product savedProduct = productRepository.save(expected);

        // when
        Product findProduct = productRepository.findById(savedProduct.getId()).get();

        // then
        assertAll(
            () -> assertThat(findProduct.getId()).isNotNull(),
            () -> assertThat(findProduct.getName()).isEqualTo(expectedName),
            () -> assertThat(findProduct.getPrice()).isEqualTo(expectedPrice),
            () -> assertThat(findProduct.getImageUrl()).isEqualTo(expectedImageUrl)
        );
    }

    @Test
    @DisplayName("Read All Test")
    void findAll() {
        // given
        Product product1 = productRepository.save(new Product("상품1", 1000, "http://product1"));
        Product product2 = productRepository.save(new Product("상품2", 2000, "http://product2"));

        // when
        List<Product> findProducts = productRepository.findAll();

        // then
        assertAll(
            () -> assertThat(findProducts.size()).isEqualTo(2),
            () -> assertThat(findProducts.get(0).getName()).isEqualTo(product1.getName()),
            () -> assertThat(findProducts.get(0).getPrice()).isEqualTo(product1.getPrice()),
            () -> assertThat(findProducts.get(0).getImageUrl()).isEqualTo(product1.getImageUrl())
        );
    }

    @Test
    @DisplayName("Delete By Id Test")
    void deleteById() {
        // given
        Product savedProduct = productRepository.save(new Product());
        Long expectedId = savedProduct.getId();
        // when
        productRepository.deleteById(savedProduct.getId());
        // then
        assertAll(
            () -> assertThat(productRepository.findById(expectedId)).isEqualTo(Optional.empty())
        );
    }

}