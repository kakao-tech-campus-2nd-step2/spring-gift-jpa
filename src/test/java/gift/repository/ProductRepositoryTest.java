package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;
    private Product savedProduct;
    private Pageable pageable = PageRequest.of(0, 10);

    @BeforeEach
    public void setUp() {
        product1 = new Product(1L, "상품", "100", "https://kakao");
        product2 = new Product(2L, "상품2", "200", "https://kakao2");
        savedProduct = productRepository.save(product1);
        productRepository.save(product2);
    }

    @Test
    void testSave() {
        assertAll(
            () -> assertThat(savedProduct.getId()).isNotNull(),
            () -> assertThat(savedProduct.getName()).isEqualTo(product1.getName()),
            () -> assertThat(savedProduct.getPrice()).isEqualTo(product1.getPrice()),
            () -> assertThat(savedProduct.getImageUrl()).isEqualTo(product1.getImageUrl())
        );
    }

    @Test
    void testFindAll() {
        Page<Product> products = productRepository.findAll(pageable);
        assertAll(
            () -> assertThat(products.getTotalElements()).isEqualTo(2),
            () -> assertThat(products.getContent().get(0).getId()).isEqualTo(product1.getId()),
            () -> assertThat(products.getContent().get(1).getId()).isEqualTo(product2.getId())
        );
    }

    @Test
    void testFindById() {
        assertAll(
            () -> assertThat(savedProduct).isNotNull(),
            () -> assertThat(savedProduct.getId()).isEqualTo(product1.getId())
        );
    }

    @Test
    void testDelete() {
        productRepository.deleteById(savedProduct.getId());
        boolean exists = productRepository.existsById(savedProduct.getId());
        assertThat(exists).isFalse();
    }
}