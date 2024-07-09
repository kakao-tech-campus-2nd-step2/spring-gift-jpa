package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import gift.domain.Product;
import gift.repository.product.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        // given
        Product product1 = new Product(1L, "아메리카노", 4500, "americano");
        // when
        Product savedProduct = productRepository.save(product1);
        // then
        Assertions.assertAll(
            () -> assertThat(savedProduct.getId()).isNotNull(),
            () -> assertThat(savedProduct.getName()).isEqualTo(product1.getName())
        );
    }

    @Test
    void findbyid() {
        // given
        Long id = 1L;
        productRepository.save(new Product(1L, "아메리카노", 4500, "americano"));
        Long findId = productRepository.findById(id).get().getId();
        assertThat(id).isEqualTo(findId);
    }

    @Test
    void deletebyid() {
        // given
        Product product1 = new Product(1L, "아메리카노", 4500, "americano");
        Product product2 = new Product(2L, "가방", 120000, "bag");
        productRepository.save(product1);
        productRepository.save(product2);

        // when
        productRepository.deleteById(1L);
        List<Product> savedProduct = productRepository.findAll();

        assertThat(savedProduct.size()).isEqualTo(1);
    }
}
