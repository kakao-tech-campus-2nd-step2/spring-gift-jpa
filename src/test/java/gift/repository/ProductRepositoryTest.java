package gift.repository;

import gift.domain.Member;
import gift.domain.Product;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository products;
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("상품 저장 테스트")
    void save() {
        // given
        Product expected = new Product("아메리카노",2000,"http://example.com/americano");

        // when
        Product actual = products.save(expected);

        // then
        assertAll(
                ()->assertThat(actual.getId()).isNotNull(),
                ()->assertThat(actual.getName()).isEqualTo(expected.getName()),
                ()->assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
                ()->assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    @DisplayName("상품 아이디 조회 테스트")
    void findById() {
        // given
        String expectedName = "아메리카노";
        int expectedPrice = 2000;
        String expectedImageUrl = "http://example.com/americano";
        Product expected = new Product(expectedName, expectedPrice, expectedImageUrl);
        Product savedProduct = products.save(expected);
        entityManager.flush();
        entityManager.clear();

        // when
        Product findProduct = products.findById(savedProduct.getId()).get();

        // then
        assertAll(
                () -> assertThat(findProduct.getId()).isNotNull(),
                () -> assertThat(findProduct.getName()).isEqualTo(expectedName),
                () -> assertThat(findProduct.getPrice()).isEqualTo(expectedPrice),
                () -> assertThat(findProduct.getImageUrl()).isEqualTo(expectedImageUrl)
        );
    }

    @Test
    @DisplayName("상품 이름 조회 테스트")
    void findByName() {
        // given
        String expectedName = "아메리카노";
        int expectedPrice = 2000;
        String expectedImageUrl = "http://example.com/americano";
        Product expected = new Product(expectedName, expectedPrice, expectedImageUrl);
        Product savedProduct = products.save(expected);
        entityManager.flush();
        entityManager.clear();

        // when
        Product findProduct = products.findByName(savedProduct.getName()).get();

        // then
        assertAll(
                () -> assertThat(findProduct.getId()).isNotNull(),
                () -> assertThat(findProduct.getName()).isEqualTo(expectedName),
                () -> assertThat(findProduct.getPrice()).isEqualTo(expectedPrice),
                () -> assertThat(findProduct.getImageUrl()).isEqualTo(expectedImageUrl)
        );
    }

    @Test
    @DisplayName("상품 전체 조회 테스트")
    void findAll() {
        // given
        Product product1 = products.save(new Product("상품1", 1000, "http://product1"));
        Product product2 = products.save(new Product("상품2", 2000, "http://product2"));
        Product product3 = products.save(new Product("상품3", 3000, "http://product3"));
        entityManager.flush();
        entityManager.clear();

        // when
        List<Product> findProducts = products.findAll();

        // then
        assertAll(
                () -> assertThat(findProducts.size()).isEqualTo(3),
                () -> assertThat(findProducts.get(0).getName()).isEqualTo(product1.getName()),
                () -> assertThat(findProducts.get(0).getPrice()).isEqualTo(product1.getPrice()),
                () -> assertThat(findProducts.get(0).getImageUrl()).isEqualTo(product1.getImageUrl())
        );
    }

    @Test
    @DisplayName("상품 아이디로 삭제 테스트")
    void deleteById() {
        // given
        String expectedName = "아메리카노";
        int expectedPrice = 2000;
        String expectedImageUrl = "http://example.com/americano";
        Product expected = new Product(expectedName, expectedPrice, expectedImageUrl);
        Product savedProduct = products.save(expected);

        // when
        products.deleteById(savedProduct.getId());

        // then
        List<Product> findProducts = products.findAll();
        assertAll(
                () -> assertThat(findProducts.size()).isEqualTo(0)
        );
    }

}