package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.entity.Member;
import gift.entity.Product;
import gift.repository.ProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품추가 테스트")
    public void addProductTest() {
        Product product = new Product("치킨", 20000, "chicken.com");
        productRepository.save(product);

        Optional<Product> findProduct = productRepository.findById(product.getId());
        assertThat(findProduct).isPresent();
        assertThat(findProduct.get().getName()).isEqualTo(product.getName());

    }

    @Test
    @DisplayName("상품 삭제 테스트")
    public void deleteProductTest() {
        Product product = new Product("치킨", 20000, "chicken.com");
        productRepository.save(product);

        productRepository.deleteById(product.getId());

        Optional<Product> findProduct = productRepository.findById(product.getId());
        assertThat(findProduct).isEmpty();
    }

    @Test
    void save() {
        Product expected = new Product("치킨", 20000, "chicken.com");
        Product actual = productRepository.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        String expectedName = "치킨";
        productRepository.save(new Product("치킨", 20000, "chicken.com"));
        boolean exists = productRepository.existsByName(expectedName);
        assertThat(exists).isTrue();
    }

}
