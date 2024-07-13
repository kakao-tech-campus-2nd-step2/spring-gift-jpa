package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.product.repository.ProductRepository;
import gift.product.dto.ProductRequest;
import gift.product.entity.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {
    
    @Autowired
    private ProductRepository productRepository;
    @Test
    void findByName() {
    }

    @Test
    @DisplayName("findById 테스트")
    void findById(){
        // given
        ProductRequest request = new ProductRequest("test", 1000, "test.jpg");
        ProductEntity expected = productRepository.save(request.toProductEntity());

        // when
        ProductEntity actual = productRepository.findById(expected.getId()).orElseThrow();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("save 테스트")
    void save(){
        // given
        ProductRequest request = new ProductRequest("test", 1000, "test.jpg");
        ProductEntity expected = request.toProductEntity();

        // when
        ProductEntity actual = productRepository.save(expected);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    @DisplayName("delete 테스트")
    void delete(){
        // given
        ProductRequest request = new ProductRequest("test", 1000, "test.jpg");
        ProductEntity savedProduct = productRepository.save(request.toProductEntity());

        // when
        productRepository.delete(savedProduct);

        // then
        assertTrue(productRepository.findById(savedProduct.getId()).isEmpty());
    }
}