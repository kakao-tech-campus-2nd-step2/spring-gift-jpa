package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import gift.entity.Product;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

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
        Long id = 1L; // data.sql에 정의된 첫 번째 제품의 ID
        Product actual = productRepository.findById(id).orElse(null);

        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo("Product 1");
    }

    @Test
    @DisplayName("Valid 조건에 맞지 않는 이름이 들어갔을 경우 오류를 던지는지 테스트")
    void edgeCaseTest() {
        Product product = new Product("아이스 아메리카노 엑스라지 사이즈", 2000, "image.jpg");

        assertThatThrownBy(() -> {
            productRepository.save(product);
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("페이지네이션 테스트")
    void paginationTest() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = productRepository.findAll(pageable);

        assertThat(page.getTotalElements()).isEqualTo(30);
        assertThat(page.getTotalPages()).isEqualTo(3);
        assertThat(page.getContent().size()).isEqualTo(10);

        assertThat(page.getContent().get(0).getName()).isEqualTo("Product 1");
    }
}
