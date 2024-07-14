package gift.EntityTest;

import gift.Model.Product;
import gift.Model.Wish;
import gift.Repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class ProductTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    void CreationTest() {
        Product product= new Product("아메리카노", 4000, "아메리카노url");
        Product actual = productRepository.save(product);
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo("아메리카노"),
                () -> assertThat(actual.getPrice()).isEqualTo(4000),
                () -> assertThat(actual.getImageUrl()).isEqualTo("아메리카노url")
        );
    }

    @Test
    void SetterTest() {
        Product product = new Product("아메리카노", 4000, "아메리카노url");
        Product actual = productRepository.save(product);
        product.setName("카푸치노");
        product.setPrice(5000);
        product.setImageUrl("카푸치노url");

        assertAll(
                () -> assertThat(actual.getName()).isEqualTo("카푸치노"),
                () -> assertThat(actual.getPrice()).isEqualTo(5000),
                () -> assertThat(actual.getImageUrl()).isEqualTo("카푸치노url")
        );
    }
}
