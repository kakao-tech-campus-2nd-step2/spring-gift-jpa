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
    private Product nullNameProduct;
    private Product emptyNameProduct;
    private Product lengthNameProduct;
    private Product invalidNameProduct;
    private Product kakaoNameProduct;
    private Product nullPriceProduct;
    private Product emptyPriceProduct;
    private Product invalidPriceProduct;
    private Product nullImageUrlProduct;
    private Product emptyImageUrlProduct;
    private Product invalidImageUrlProduct;

    @BeforeEach
    public void setUp() {
        product1 = new Product(1L, "상품", "100", "https://kakao");
        product2 = new Product(2L, "상품2", "200", "https://kakao2");
        nullNameProduct = new Product(1L, null,"100", "https://kakao");
        emptyNameProduct = new Product(1L, "", "200", "https://kakao");
        lengthNameProduct = new Product(1L, "aaaa aaaa aaaa a", "200", "https://kakao");
        invalidNameProduct = new Product(1L, ".", "100", "https://kakao");
        kakaoNameProduct = new Product(1L, "카카오", "100", "https://kakao");
        nullPriceProduct = new Product(1L, "상품",null, "https://kakao");
        emptyPriceProduct = new Product(1L, "상품", "", "https://kakao");
        invalidPriceProduct = new Product(1L, "상품", "abcde", "https://kakao");
        nullImageUrlProduct = new Product(1L, "상품", "100", null);
        emptyImageUrlProduct = new Product(1L, "상품", "100", "");
        invalidImageUrlProduct = new Product(1L, "상품", "100", "kbm");
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
            nullNameProduct.validate();
            productRepository.save(nullNameProduct);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithEmptyName() {
        try {
            emptyNameProduct.validate();
            productRepository.save(emptyNameProduct);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithLengthName() {
        try {
            lengthNameProduct.validate();
            productRepository.save(lengthNameProduct);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithInvalidName() {
        try {
            invalidNameProduct.validate();
            productRepository.save(invalidNameProduct);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithKaKaoName() {
        try {
            kakaoNameProduct.validate();
            productRepository.save(kakaoNameProduct);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }


    @Test
    void testSaveWithNullPrice() {
        try {
            nullPriceProduct.validate();
            productRepository.save(nullPriceProduct);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithEmptyPrice() {
        try {
            emptyPriceProduct.validate();
            productRepository.save(emptyPriceProduct);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }


    @Test
    void testSaveWithInvalidPrice() {
        try {
            invalidPriceProduct.validate();
            productRepository.save(invalidPriceProduct);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithNullImageUrl() {
        try {
            nullImageUrlProduct.validate();
            productRepository.save(nullImageUrlProduct);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithEmptyImageUrl() {
        try {
            emptyImageUrlProduct.validate();
            productRepository.save(emptyImageUrlProduct);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithInvalidImageUrl() {
        try {
            invalidImageUrlProduct.validate();
            productRepository.save(invalidImageUrlProduct);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

}