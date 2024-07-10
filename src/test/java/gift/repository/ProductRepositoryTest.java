package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import gift.domain.Product;
import gift.repository.product.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("상품 정보 저장 테스트")
    @Test
    void save() {
        // given
        Product product1 = new Product("아메리카노", 4500, "americano");
        // when
        Product savedProduct = productRepository.save(product1);
        // then
        Assertions.assertAll(
            () -> assertThat(savedProduct.getId()).isNotNull(),
            () -> assertThat(savedProduct.getName()).isEqualTo(product1.getName())
        );
    }

    @DisplayName("id에 따른 상품 찾기 테스트")
    @Test
    void findbyid() {
        // given
        Long id = 1L;
        productRepository.save(new Product("아메리카노", 4500, "americano"));
        Long findId = productRepository.findById(id).get().getId();
        assertThat(id).isEqualTo(findId);
    }

    @DisplayName("상품 삭제 기능 테스트")
    @Test
    void deletebyid() {
        // given
        Product product1 = new Product("아메리카노", 4500, "americano");
        Product product2 = new Product("가방", 120000, "bag");
        productRepository.save(product1);
        productRepository.save(product2);

        // when
        productRepository.deleteById(1L);
        List<Product> savedProduct = productRepository.findAll();

        assertThat(savedProduct.size()).isEqualTo(1);
    }
}
