package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.Product;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository products;

    @Test
    void save() {
        Product expected = new Product(1L, "Product A", 4500, "image-Product_A.com");
        Product actual = products.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    void find() {
        Product expected = new Product(1L, "Product A", 4500, "image-Product_A.com");
        Product actual = products.save(expected);
        assertThat(actual).isEqualTo(products.findById(1L));
    }

    @Test
    void update() {
        Product current = new Product(1L, "Product A", 4500, "image-Product_A.com");
        products.save(current);
        Product update = new Product(1L, "Product A", 5500, "image-Product_B.com");
        Product actual = products.save(update);
        assertThat(actual.getPrice()).isEqualTo(5500);
    }

    @Test
    void delete() {
        Product product = new Product(1L, "Product A", 4500, "image-Product_A.com");
        products.save(product);
        products.deleteById(product.getId());
        assertThat(products.findAll()).isEmpty();
    }

}
