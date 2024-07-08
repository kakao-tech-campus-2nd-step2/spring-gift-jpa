package gift.dao;

import gift.product.dao.ProductRepository;
import gift.product.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 추가 및 ID 조회 테스트")
    void saveAndFindById() {
        Product product = new Product(null, "newproduct", 12345, "new.jpg");
        Product savedProduct = productRepository.save(product);

        Product foundProduct = productRepository.findById(savedProduct.getId())
                .orElse(null);

        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getName()).isEqualTo(savedProduct.getName());
        assertThat(foundProduct.getPrice()).isEqualTo(savedProduct.getPrice());
        assertThat(foundProduct.getImageUrl()).isEqualTo(savedProduct.getImageUrl());
    }

    @Test
    @DisplayName("상품 ID 조회 실패 테스트")
    void findByIdFailed() {
        Product product = new Product(null, "newproduct", 12345, "new.jpg");
        Product savedProduct = productRepository.save(product);

        Product foundProduct = productRepository.findById(123456789L)
                .orElse(null);

        assertThat(foundProduct).isNull();
    }

    @Test
    @DisplayName("상품 ID 리스트 조회 테스트")
    void findByIds() {
        List<Long> productIds = Arrays.asList(1L, 2L, 3L, 4L);
        productRepository.save(new Product(null, "product1L", 1000, "1L.jpg"));
        productRepository.save(new Product(null, "product2L", 2000, "2L.jpg"));
        productRepository.save(new Product(null, "product3L", 3000, "3L.jpg"));
        productRepository.save(new Product(null, "product4L", 4000, "4L.jpg"));

        List<Product> products = productRepository.findByIds(productIds);

        assertThat(products.size()).isEqualTo(productIds.size());
        assertThat(products.get(1)
                .getName())
                .isEqualTo("product1L");
        assertThat(products.get(2)
                .getName())
                .isEqualTo("product2L");
        assertThat(products.get(3)
                .getName())
                .isEqualTo("product3L");
        assertThat(products.get(4)
                .getName())
                .isEqualTo("product4L");
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void updateProduct() {
        Product product = new Product(null, "product1", 1000, "product1.jpg");
        Product savedProduct = productRepository.save(product);
        savedProduct = new Product(savedProduct.getId(), "updateproduct", 12345, "updateproduct.jpg");

        Product updatedProduct = productRepository.save(savedProduct);

        Product foundProduct = productRepository.findById(updatedProduct.getId())
                .orElse(null);
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getName()).isEqualTo(savedProduct.getName());
        assertThat(foundProduct.getPrice()).isEqualTo(savedProduct.getPrice());
        assertThat(foundProduct.getImageUrl()).isEqualTo(savedProduct.getImageUrl());
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteProduct() {
        Product product = new Product(null, "product", 1000, "product.jpg");
        Product savedProduct = productRepository.save(product);

        productRepository.deleteById(savedProduct.getId());

        boolean exists = productRepository.existsById(savedProduct.getId());
        assertThat(exists).isFalse();
    }

}