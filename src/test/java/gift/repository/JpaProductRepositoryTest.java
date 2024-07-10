package gift.repository;

import gift.config.SpringConfig;
import gift.model.Product;
import gift.model.ProductDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(SpringConfig.class)
public class JpaProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        // given
        ProductDTO actual = new ProductDTO("abc", 123, "test.com");

        // when
        Product expected = productRepository.save(actual);

        // then
        Assertions.assertAll(
                () -> assertThat(expected.getId()).isNotNull(),
                () -> assertThat(expected.getName()).isEqualTo(actual.getName()),
                () -> assertThat(expected.getPrice()).isEqualTo(actual.getPrice()),
                () -> assertThat(expected.getImageUrl()).isEqualTo(actual.getImageUrl())
        );
    }

    @Test
    void find() {
        // given
        ProductDTO actual = new ProductDTO("abc", 123, "test.com");

        // when
        Product saved = productRepository.save(actual);
        Long id = saved.getId();
        Product expected = productRepository.findById(id);

        // then
        Assertions.assertAll(
                () -> assertThat(expected.getId()).isNotNull(),
                () -> assertThat(expected.getName()).isEqualTo(actual.getName()),
                () -> assertThat(expected.getPrice()).isEqualTo(actual.getPrice()),
                () -> assertThat(expected.getImageUrl()).isEqualTo(actual.getImageUrl())
        );
    }

    @Test
    void edit() {
        // given
        ProductDTO product = new ProductDTO("abc", 123, "test.com");
        Product saved = productRepository.save(product);
        Long id = saved.getId();

        // when
        ProductDTO actual = new ProductDTO("def", 456, "test1.com");
        Product expected = productRepository.edit(id, actual);

        // then
        Assertions.assertAll(
                () -> assertThat(expected.getId()).isNotNull(),
                () -> assertThat(expected.getName()).isEqualTo(actual.getName()),
                () -> assertThat(expected.getPrice()).isEqualTo(actual.getPrice()),
                () -> assertThat(expected.getImageUrl()).isEqualTo(actual.getImageUrl())
        );
    }

    @Test
    void delete() {
        // given
        ProductDTO product = new ProductDTO("abc", 123, "test.com");
        Product saved = productRepository.save(product);
        Long id = saved.getId();

        // when
        productRepository.delete(id);
        Product actual = productRepository.findById(id);

        // then
        assertThat(actual).isNull();
    }
}
