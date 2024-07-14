package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.product.Product;
import gift.repository.product.ProductJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductJpaRepositoryTest {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Test
    public void save() {
        // given
        Product product = new Product(1L, "product1", 1000, "product1.jpg");
        // when
        productJpaRepository.save(product);
        // then
        assertAll(
            () -> assertThat(productJpaRepository.findById(1L).get().getName()).isEqualTo(
                "product1"),
            () -> assertThat(productJpaRepository.findById(1L).get().getPrice()).isEqualTo(1000),
            () -> assertThat(productJpaRepository.findById(1L).get().getImageUrl()).isEqualTo(
                "product1.jpg")
        );
    }

    @Test
    public void delete() {
        // given
        Product product = new Product(1L, "product1", 1000, "product1.jpg");
        productJpaRepository.save(product);
        // when
        productJpaRepository.deleteById(1L);
        // then
        assertThat(productJpaRepository.findById(1L)).isEmpty();
    }

    @Test
    public void update() {
        // given
        Product product = new Product(1L, "product1", 1000, "product1.jpg");
        productJpaRepository.save(product);
        // when
        product.update("product2", 2000, "product2.jpg");
        productJpaRepository.save(product);
        // then
        assertAll(
            () -> assertThat(productJpaRepository.findById(1L).get().getName()).isEqualTo(
                "product2"),
            () -> assertThat(productJpaRepository.findById(1L).get().getPrice()).isEqualTo(2000),
            () -> assertThat(productJpaRepository.findById(1L).get().getImageUrl()).isEqualTo(
                "product2.jpg")
        );
    }

    @Test
    public void findById() {
        // given
        Product product = new Product(1L, "product1", 1000, "product1.jpg");
        productJpaRepository.save(product);
        // when
        Product findProduct = productJpaRepository.findById(1L).get();
        // then
        assertAll(
            () -> assertThat(findProduct.getName()).isEqualTo("product1"),
            () -> assertThat(findProduct.getPrice()).isEqualTo(1000),
            () -> assertThat(findProduct.getImageUrl()).isEqualTo("product1.jpg")
        );
    }
}
