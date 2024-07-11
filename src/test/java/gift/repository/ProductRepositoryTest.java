package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import gift.model.product.Product;
import gift.repository.product.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product("아메리카노", 4500, "americano");
        product2 = new Product("가방", 120000, "bag");
        productRepository.save(product1);
        productRepository.save(product2);
    }
    @DisplayName("상품 정보 저장 테스트")
    @Test
    void save() {
        // given
        Product product3 = new Product("노트북", 1500000, "laptop");
        // when
        Product savedProduct = productRepository.save(product3);
        // then
        Assertions.assertAll(
            () -> assertThat(savedProduct.getId()).isNotNull(),
            () -> assertThat(savedProduct.getName()).isEqualTo(product3.getName())
        );
    }

    @DisplayName("id에 따른 상품 찾기 테스트")
    @Test
    void findbyid() {
        // given
        Long id = product1.getId();
        // when
        Product foundProduct = productRepository.findById(id).orElse(null);
        Long findId = foundProduct.getId();
        // then
        assertThat(id).isEqualTo(findId);
    }

    @DisplayName("상품 삭제 기능 테스트")
    @Test
    void deletebyid() {
        // given
        Long DeleteId = product1.getId();

        // when
        productRepository.deleteById(DeleteId);
        List<Product> remainingProducts = productRepository.findAll();

        // then
        assertThat(remainingProducts.size()).isEqualTo(1);
    }
}
