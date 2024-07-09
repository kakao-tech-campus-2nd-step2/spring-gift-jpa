package gift.product.repository;

import gift.product.domain.Product;
import gift.product.domain.ProductName;
import gift.product.domain.ProductPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void beforeEach() {
        product = new Product(null, new ProductName("name"), new ProductPrice(10L), "imageUrl");
    }

    @Test
    @Description("save 테스트")
    void saveTest() {
        // given
        // when
        Product savedProduct = productRepository.save(product);

        // then
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo(product.getName());
        assertThat(savedProduct.getPrice()).isEqualTo(product.getPrice());
        assertThat(savedProduct.getImageUrl()).isEqualTo(product.getImageUrl());
    }

    @Test
    @Description("findById 테스트")
    void findByIdTest() {
        // given
        Product savedProduct = productRepository.save(product);

        // when
        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());

        // then
        assertThat(foundProduct).contains(product);
    }

    @Test
    @Description("update 테스트")
    void updateTest() {
        // given
        Product savedProduct = productRepository.save(product);

        // when
        savedProduct = new Product(savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice(), savedProduct.getImageUrl());
        Product updateProduct = productRepository.save(savedProduct);

        // then
        assertThat(updateProduct).isEqualTo(savedProduct);
    }

    @Test
    @Description("deleteById 테스트")
    void deleteTest() {
        // given
        Product savedProduct = productRepository.save(product);

        // when
        productRepository.deleteById(savedProduct.getId());
        Optional<Product> products = productRepository.findById(product.getId());

        // then
        assertThat(products).isEmpty();
    }
}