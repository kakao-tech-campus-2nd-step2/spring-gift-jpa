package gift.repository;

import gift.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 저장")
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
    @DisplayName("상품 읽기(read)")
    void readTest() {
        // Given
        Product product1 = new Product("아몬드", 500, "image.jpg");
        Product product2 = new Product("초코", 5400, "image2.jpg");
        productRepository.save(product1);
        productRepository.save(product2);

        // When
        List<Product> foundProducts = productRepository.findAll();

        // Then
        assertThat(foundProducts).hasSize(2);
        assertThat(foundProducts).containsExactly(product1, product2);
    }

    @Test
    @DisplayName("상품 수정")
    void updateTest() {
        // Given
        Product product = new Product("아몬드", 500, "image.jpg");
        Product savedProduct = productRepository.save(product);

        // When
        savedProduct.change("아몬드봉봉", 600, "image.jpg");

        // Then
        assertThat(savedProduct.getName()).isEqualTo("아몬드봉봉");
    }

    @Test
    @DisplayName("상품 삭제")
    void deleteTest() {
        // Given
        Product product = new Product("아몬드", 500, "image.jpg");
        Product savedProduct = productRepository.save(product);
        Long savedProductId = savedProduct.getId();

        // When
        productRepository.delete(savedProduct);
        Optional<Product> deleteResult = productRepository.findById(savedProductId);

        // Then
        assertThat(deleteResult).isNotPresent();
    }

}
