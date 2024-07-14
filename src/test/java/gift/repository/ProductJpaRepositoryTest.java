package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.product.Product;
import gift.repository.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductJpaRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void save() {
        // given
        Product product = new Product(1L, "product1", 1000, "product1.jpg");
        // when
        productRepository.save(product);
        // then
        assertAll(
            () -> assertThat(productRepository.findById(1L).get().getName()).isEqualTo(
                "product1"),
            () -> assertThat(productRepository.findById(1L).get().getPrice()).isEqualTo(1000),
            () -> assertThat(productRepository.findById(1L).get().getImageUrl()).isEqualTo(
                "product1.jpg")
        );
    }

    @Test
    public void delete() {
        // given
        Product product = new Product(1L, "product1", 1000, "product1.jpg");
        productRepository.save(product);
        // when
        productRepository.deleteById(1L);
        // then
        assertThat(productRepository.findById(1L)).isEmpty();
    }

    @Test
    public void update() {
        // given
        Product product = new Product(1L, "product1", 1000, "product1.jpg");
        productRepository.save(product);
        // when
        product.update("product2", 2000, "product2.jpg");
        productRepository.save(product);
        // then
        assertAll(
            () -> assertThat(productRepository.findById(1L).get().getName()).isEqualTo(
                "product2"),
            () -> assertThat(productRepository.findById(1L).get().getPrice()).isEqualTo(2000),
            () -> assertThat(productRepository.findById(1L).get().getImageUrl()).isEqualTo(
                "product2.jpg")
        );
    }

    @Test
    public void findById() {
        // given
        Product product = new Product(1L, "product1", 1000, "product1.jpg");
        productRepository.save(product);
        // when
        Product findProduct = productRepository.findById(1L).get();
        // then
        assertAll(
            () -> assertThat(findProduct.getName()).isEqualTo("product1"),
            () -> assertThat(findProduct.getPrice()).isEqualTo(1000),
            () -> assertThat(findProduct.getImageUrl()).isEqualTo("product1.jpg")
        );
    }
}
