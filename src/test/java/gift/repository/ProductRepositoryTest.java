package gift.repository;

import gift.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        Product expected = new Product("cakes",
                                       4500L,
                                       "www.cakes.com");

        Product actual = productRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );
    }

    @Test
    void finAll() {
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(3);
    }

    @Test
    void findAllById() {
        Product product1 = new Product("cookie", 2000L, "www.cookie.com");
        Product product2 = new Product("pie", 5000L, "www.pie.com");
        productRepository.save(product1);
        productRepository.save(product2);

        List<Long> ids = Arrays.asList(product1.getId(), product2.getId());
        List<Product> products = productRepository.findAllById(ids);

        assertThat(products).hasSize(2).contains(product1, product2);
    }

    @Test
    void deleteById() {
        productRepository.deleteById(1L);
        Optional<Product> deletedProduct = productRepository.findById(1L);

        assertThat(deletedProduct).isEmpty();
    }

}