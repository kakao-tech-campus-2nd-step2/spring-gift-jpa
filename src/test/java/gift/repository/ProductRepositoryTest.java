package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private String name = "product";
    private int price = 10000;
    private String imageUrl = "image.jpg";

    @BeforeEach
    void setUp() {
        productRepository.save(new Product(name, price, imageUrl));
    }

    @Test
    void saveProductTest() {
        var expected = new Product(name, price, imageUrl);
        var actual = productRepository.findAll().getFirst();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findProductByName() {
        var expected = new Product(name, price, imageUrl);
        var actual = productRepository.findByName(name);
        assertTrue(actual.isPresent());
        assertThat(actual.get()).isEqualTo(expected);
    }


    @Test
    void deleteProductByIdTest() {
        var expected = new Product("sampleName", 10000, "product.jpg");
        var sample2 = new Product("sample2", 10200, "product2.jpg");

        productRepository.save(expected);
        productRepository.save(sample2);

        productRepository.deleteById(expected.getId());

        assertAll(
            () -> assertThat(productRepository.findById(expected.getId()).isPresent()).isFalse(),
            () -> assertThat(productRepository.findAll().isEmpty()).isFalse()
        );

    }
}