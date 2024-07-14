package gift.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import gift.model.Product;
import gift.repository.ProductRepository;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
    	Product expected = new Product("아이스 아메리카노 T", 4500, "https://example.com/image.jpg");
        Product actual = productRepository.save(expected);
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }

    @Test
    void findById() {
    	Product expected = new Product("아이스 아메리카노 T", 4500, "https://example.com/image.jpg");
        productRepository.save(expected);
        Product actual = productRepository.findById(expected.getId()).orElse(null);
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }
}