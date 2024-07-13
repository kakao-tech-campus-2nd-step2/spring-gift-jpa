package gift.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    void save() {
        Product expected = new Product("사과",2000,"www");
        Product actual = productRepository.save(expected);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("단일 상품 조회 테스트")
    void findById() {
        String expectedName = "사과";
        int expectedPrice = 2000;
        String expectedImageUrl = "www";
        Product expected = new Product(expectedName, expectedPrice, expectedImageUrl);
        productRepository.save(expected);
        Product actual = productRepository.findById(expected.getId()).get();
        assertThat(actual).isEqualTo(expected);

        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
            () -> assertThat(actual.getName()).isEqualTo(expectedName),
            () -> assertThat(actual.getPrice()).isEqualTo(expectedPrice),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expectedImageUrl)
        );
    }

    @Test
    @DisplayName("모든 상품 조회 테스트")
    void findAll() {
        Product product1 = new Product("사과", 2000, "www");
        Product product2 = new Product("앵우", 100000, "www.com");
        Product product3 = new Product("econo", 30000, "error");
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        List<Product> productList = productRepository.findAll();

        assertAll(
            () -> assertThat(productList.size()).isEqualTo(3)
        );
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void update() {
        Product product = new Product("사과", 2000, "www");

        product.update("바나나", 3000, "www.com");

        assertAll(
            () -> assertThat(product.getName()).isEqualTo("바나나"),
            () -> assertThat(product.getPrice()).isEqualTo(3000),
            () -> assertThat(product.getImageUrl()).isEqualTo("www.com")
        );
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void delete() {
        Product product = new Product("사과", 2000, "www");
        productRepository.save(product);
        productRepository.deleteById(product.getId());

        List<Product> isProduct = productRepository.findById(product.getId()).stream().toList();

        assertAll(
            () -> assertThat(isProduct.size()).isEqualTo(0)
        );
    }
}
