package gift.repositoryTest;

import gift.model.Product;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveAndFindProduct() {
        Product product = new Product("상품1", 1000, "http://example.com/image.jpg");
        productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findById(product.getId());
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("상품1");
    }

    @Test
    void testSaveValidNameProduct() {
        assertThrows(IllegalArgumentException.class, () -> {
            Product product = new Product("카카오", 1000, "http://example.com/image.jpg");
            productRepository.save(product);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Product product = new Product("kakao", 1000, "http://example.com/image.jpg");
            productRepository.save(product);
        });

        Product validProduct = new Product("상품2", 2000, "http://example.com/image2.jpg");
        productRepository.save(validProduct);
        Optional<Product> foundProduct = productRepository.findById(validProduct.getId());
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("상품2");
    }
}
