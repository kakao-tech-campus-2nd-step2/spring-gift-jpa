package gift;

import gift.Model.Entity.ProductEntity;
import gift.Repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class ProductEntityRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void save(){
        ProductEntity expected = new ProductEntity("a", 1000, "b");
        ProductEntity actual = productRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        String expectedName = "a";
        int expectedPrice = 1000;
        String expectedImageUrl = "b";
        productRepository.save(new ProductEntity(expectedName, expectedPrice, expectedImageUrl));
        String actual = productRepository.findByName(expectedName).get().getName();
        assertThat(actual).isEqualTo(expectedName);
    }
}
