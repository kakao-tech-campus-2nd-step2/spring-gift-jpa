package gift;

import gift.model.Product;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class JpaProductTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    void saveProduct() {
        Product product = new Product(1L, "name", 134, "asdf");
        Product real = productRepository.save(product);
        assertAll(
                () -> assertThat(real.getId()).isNotNull(),
                () -> assertThat(real.getName()).isEqualTo(product.getName()),
                () -> assertThat(real.getImageUrl()).isEqualTo(product.getImageUrl()),
                () -> assertThat(real.getPrice()).isEqualTo(product.getPrice())
        );
    }
}
