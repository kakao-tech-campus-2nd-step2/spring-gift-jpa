package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.Product;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    private ProductRepository productRepository;

    @Autowired
    public ProductRepositoryTest(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Test
    void testSave() {
        Product product = new Product(1L, "상품", "100", "https://kakao");
        Product savedProduct = productRepository.save(product);
        assertAll(
            () -> assertThat(savedProduct.getId()).isNotNull(),
            () -> assertThat(savedProduct.getName()).isEqualTo(product.getName()),
            () -> assertThat(savedProduct.getPrice()).isEqualTo(product.getPrice()),
            () -> assertThat(savedProduct.getImageUrl()).isEqualTo(product.getImageUrl())
        );
    }

    @Test
    void testFindAll() {
        Product product1 = new Product(1L, "상품1", "100", "https://kakao1");
        Product product2 = new Product(2L, "상품2", "200", "https://kakao2");
        productRepository.save(product1);
        productRepository.save(product2);
        List<Product> products = productRepository.findAll();
        assertAll(
            () -> assertThat(products.size()).isEqualTo(2),
            () -> assertThat(products.get(0).getId()).isEqualTo(product1.getId()),
            () -> assertThat(products.get(1).getId()).isEqualTo(product2.getId())
        );
    }

    @Test
    void testFindById() {
        Product product = new Product(1L, "상품", "100", "https://kakao");
        Product savedProduct = productRepository.save(product);
        assertAll(
            () -> assertThat(savedProduct).isNotNull(),
            () -> assertThat(savedProduct.getId()).isEqualTo(product.getId())
        );
    }

    @Test
    void testDelete() {
        Product product = new Product(1L, "상품", "100", "https://kakao");
        Product savedProduct = productRepository.save(product);
        productRepository.deleteById(savedProduct.getId());
        boolean exists = productRepository.existsById(savedProduct.getId());
        assertThat(exists).isFalse();
    }

}