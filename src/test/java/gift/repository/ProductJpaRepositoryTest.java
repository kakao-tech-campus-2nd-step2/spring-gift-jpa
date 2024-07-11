package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.domain.Product;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductJpaRepositoryTest {

    @Autowired
    ProductJpaRepository productJpaRepository;

    @BeforeEach
    void before(){
        String name = "product1";
        int price = 1000;
        String imageUrl = "product1.jpg";
        Product product = new Product(name, price, imageUrl);
        productJpaRepository.save(product);
    }
    @Test
    @DisplayName("상품 이름으로 찾기")
    void findByName() {
        String name = "product1";
        Product product = productJpaRepository.findByName(name).get();
        assertAll(
            () -> assertThat(product.getId()).isNotNull(),
            () -> assertThat(product.getName()).isEqualTo(name)
        );
    }
}