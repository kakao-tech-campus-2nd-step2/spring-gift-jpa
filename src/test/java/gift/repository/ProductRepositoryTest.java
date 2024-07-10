package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;



    @Test
    void saveProductTest() {
        var expected = new Product("newSample", 10000, "productNew.jpg");
        var actual = productRepository.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByIdTest() {
        var expected = new Product("sampleName", 10000, "product.jpg");
        var actual = productRepository.save(expected);
        assertAll(
            () -> assertThat(productRepository.findById(expected.getId()).isPresent()).isEqualTo(
                true),
            () -> {
                var present = productRepository.findById(expected.getId()).isPresent();
                if (present) {
                    assertThat(productRepository.findById(expected.getId()).get()).isEqualTo(
                        expected);
                }
            }
        );
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
            ()->assertThat(productRepository.findAll().isEmpty()).isFalse()
        );

    }
}