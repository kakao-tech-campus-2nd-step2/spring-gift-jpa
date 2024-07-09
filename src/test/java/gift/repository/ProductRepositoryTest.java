package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.entity.Product;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("save 테스트")
    void saveTest() {
        Product expected = new Product("gift", 1000, "image.jpg");
        Product actual = productRepository.save(expected);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }

    @Test
    @DisplayName("DB에 저장된 ID를 기반으로 저장된 객체를 불러오는지 테스트")
    void findByIdTest() {
        Product expected = new Product("gift", 1000, "image.jpg");
        Product product = productRepository.save(expected);
        Long id = product.getId();
        Product actual = productRepository.findById(id).orElse(null);

        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }

    @Test
    @DisplayName("Valid 조건에 맞지 않는 이름이 들어갔을 경우 오류를 던지는지 테스트")
    void edgeCaseTest() {
        Product product = new Product("카카오", 2000, "image.jpg");

        assertThrows(ConstraintViolationException.class, () -> {
            productRepository.save(product);
        });
    }
}
