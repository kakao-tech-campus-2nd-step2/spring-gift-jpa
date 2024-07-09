package gift.product;

import gift.product.infrastructure.persistence.JpaProductRepository;
import gift.product.infrastructure.persistence.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTests {
    @Autowired
    private JpaProductRepository jpaProductRepository;

    @Test
    public void saveProduct() {
        ProductEntity product = new ProductEntity("test", 100, "test");

        product = jpaProductRepository.save(product);

        assertThat(jpaProductRepository.findById(product.getId())).isPresent();
        assertThat(jpaProductRepository.findById(product.getId()).get()).isEqualTo(product);
    }

    @Test
    public void findProductById() {
        ProductEntity product = new ProductEntity("test", 100, "test");
        product = jpaProductRepository.save(product);

        assertThat(jpaProductRepository.findById(product.getId())).isPresent();
    }

    @Test
    public void deleteProduct() {
        ProductEntity product = new ProductEntity("test", 100, "test");
        product = jpaProductRepository.save(product);

        jpaProductRepository.deleteById(product.getId());

        assertThat(jpaProductRepository.findById(product.getId())).isEmpty();
    }
}
