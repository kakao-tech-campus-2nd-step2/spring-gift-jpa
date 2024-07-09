package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.Name;
import gift.model.Product;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        Product expected = new Product(null, new Name("테스트상품"), 1000, "test.jpg");
        Product actual = productRepository.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName().getName()).isEqualTo(expected.getName().getName())
        );
    }

    @Test
    void findByName() {
        String expected = "테스트상품";
        productRepository.save(new Product(null, new Name(expected), 1000, "test.jpg"));
        Product actual = productRepository.findByName_Name(expected);
        assertThat(actual.getName().getName()).isEqualTo(expected);
    }
}