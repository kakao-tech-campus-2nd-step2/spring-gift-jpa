package gift;

import static org.assertj.core.api.Assertions.assertThat;

import gift.product.Product;
import gift.product.ProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void save(){
        Product product = new Product(1000,"라이언","image.jpg");
        Product actual = productRepository.save(product);
        assertThat(actual.getName()).isEqualTo("라이언");
        assertThat(actual.getPrice()).isEqualTo(1000);
        assertThat(actual.getImageUrl()).isEqualTo("image.jpg");
    }

    @Test
    void findById() {
        Product product = new Product(1000,"라이언","image.jpg");
        productRepository.save(product);
        Optional<Product> actual = productRepository.findById(product.getId());
        assertThat(actual).isPresent();
        assertThat(actual.get().getName()).isEqualTo("라이언");
    }

    @Test
    void deleteById() {
        Product product = new Product(1000,"라이언","image.jpg");
        productRepository.deleteById(product.getId());
        Optional<Product> actual = productRepository.findById(product.getId());
        assertThat(actual).isNotPresent();
    }
}
