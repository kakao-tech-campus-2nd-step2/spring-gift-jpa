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

    @Autowired
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;
    private Product invalidProduct;

    @BeforeEach
    public void setUp() {
        product1 = new Product(1L, "상품", "100", "https://kakao");
        product2 = new Product(2L, "상품2", "200", "https://kakao2");
    }

    @Test
    void testSave() {
        product1.validate();
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
        product1.validate();
        product2.validate();
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
        product1.validate();
        Product savedProduct = productRepository.save(product1);
        assertAll(
            () -> assertThat(savedProduct).isNotNull(),
            () -> assertThat(savedProduct.getId()).isEqualTo(product1.getId())
        );
    }

    @Test
    void testDelete() {
        product1.validate();
        Product savedProduct = productRepository.save(product1);
        productRepository.deleteById(savedProduct.getId());
        boolean exists = productRepository.existsById(savedProduct.getId());
        assertThat(exists).isFalse();
    }

    @Test
    void testSaveWithNullName() {
        try {
            invalidProduct = new Product(1L, null, "100", "https://kakao");
            invalidProduct.validate();
            productRepository.save(invalidProduct);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithLengthName() {
        try {
            invalidProduct = new Product(1L, "aaaaaaaaa aaaa aa", "100", "https://kakao");
            invalidProduct.validate();
            productRepository.save(invalidProduct);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithSpecial() {
        try {
            invalidProduct = new Product(1L, ".@", "100", "https://kakao");
            invalidProduct.validate();
            productRepository.save(invalidProduct);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithKaKaoName() {
        try {
            invalidProduct = new Product(1L, "카카오상품", "100", "https://kakao");
            invalidProduct.validate();
            productRepository.save(invalidProduct);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithNullPrice() {
        try {
            invalidProduct = new Product(1L, "상품", null, "https://kakao");
            invalidProduct.validate();
            productRepository.save(invalidProduct);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithInvalidPrice() {
        try {
            invalidProduct = new Product(1L, "상품", "가격", "https://kakao");
            invalidProduct.validate();
            productRepository.save(invalidProduct);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithNullImageUrl() {
        try {
            invalidProduct = new Product(1L, "상품", "100", null);
            invalidProduct.validate();
            productRepository.save(invalidProduct);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithInvalidImageUrl() {
        try {
            Product invalidProduct = new Product(1L, "상품", "100", "kakao");
            invalidProduct.validate();
            productRepository.save(invalidProduct);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

}