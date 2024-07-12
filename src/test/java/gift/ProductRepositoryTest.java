package gift;

import gift.entity.Product;
import gift.repository.ProductRepositoryInterface;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
@Transactional
@DataJpaTest
public class ProductRepositoryTest {

    private ProductRepositoryInterface products;

    public ProductRepositoryTest(ProductRepositoryInterface products) {
        this.products = products;
    }

    @Test
    void save() {
        final Product product = new Product("사과", 500L, "naver.com");
        assertThat(product.getId()).isNull();
        var actualProduct = products.save(product);
        assertThat(actualProduct.getId()).isNotNull();
    }

    @Test
    void findByName() {
        products.save(new Product("사과", 500L, "naver.com"));
        final Product actualProduct = products.findByName("사과");
        assertThat(actualProduct.getId()).isNotNull();
        assertThat(actualProduct.getName()).isEqualTo("사과");
    }
}
