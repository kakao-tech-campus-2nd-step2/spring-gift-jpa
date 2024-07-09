package gift;

import gift.Model.Product;
import gift.Repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class UserInfoRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void save(){
        Product expected = new Product("a", 1000, "b");
        Product actual = productRepository.save(expected);
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
        productRepository.save(new Product(expectedName, expectedPrice, expectedImageUrl));
        String actual = productRepository.findByName(expectedName).getName();
        assertThat(actual).isEqualTo(expectedName);
    }
}
