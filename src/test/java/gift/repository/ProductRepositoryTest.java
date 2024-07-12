package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.Product;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    private ProductRepository productRepository;

    private Product product1;
    private Product product2;

    @Autowired
    public ProductRepositoryTest(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @BeforeEach
    public void setUp() {
        product1 = new Product(1L, "상품", "100", "https://kakao");
        product2 = new Product(2L, "상품2", "200", "https://kakao2");
    }

    @Test
    void testSave() {
        Product savedProduct = productRepository.save(product1);
        assertAll(
            () -> assertThat(savedProduct.getId()).isNotNull(),
            () -> assertThat(savedProduct.getName()).isEqualTo(product1.getName()),
            () -> assertThat(savedProduct.getPrice()).isEqualTo(product1.getPrice()),
            () -> assertThat(savedProduct.getImageUrl()).isEqualTo(product1.getImageUrl())
        );
    }

    @Test
    void testFindAll() {
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
        Product savedProduct = productRepository.save(product1);
        assertAll(
            () -> assertThat(savedProduct).isNotNull(),
            () -> assertThat(savedProduct.getId()).isEqualTo(product1.getId())
        );
    }

    @Test
    void testDelete() {
        Product savedProduct = productRepository.save(product1);
        productRepository.deleteById(savedProduct.getId());
        boolean exists = productRepository.existsById(savedProduct.getId());
        assertThat(exists).isFalse();
    }

}