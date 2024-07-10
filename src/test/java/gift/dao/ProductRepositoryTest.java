package gift.dao;

import gift.product.dao.ProductRepository;
import gift.product.dto.ProductRequest;
import gift.product.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 추가 및 ID 조회 테스트")
    void saveAndFindById() {
        Product product = new Product("newproduct", 12345, "new.jpg");
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
        Product product = new Product("newproduct", 12345, "new.jpg");
        Product savedProduct = productRepository.save(product);

        Product foundProduct = productRepository.findById(123456789L)
                .orElse(null);

        assertThat(foundProduct).isNull();
    }

    @Test
    @DisplayName("상품 ID 리스트 조회 테스트")
    void findByIds() {
        List<Long> productIds = new ArrayList<>();
        productIds.add(
                productRepository.save(
                        new Product(
                                "product1L",
                                1000,
                                "1L.jpg")
                        ).getId()
        );
        productIds.add(
                productRepository.save(
                        new Product(
                                "product2L",
                                2000,
                                "2L.jpg")
                ).getId()
        );
        productIds.add(
                productRepository.save(
                        new Product(
                                "product3L",
                                3000,
                                "3L.jpg")
                ).getId()
        );
        productIds.add(
                productRepository.save(
                        new Product(
                                "product4L",
                                4000,
                                "4L.jpg")
                ).getId()
        );

        List<Product> products = productRepository.findAll();

        assertThat(products.size()).isEqualTo(productIds.size());
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void updateProduct() {
        Product product = new Product("product1", 1000, "product1.jpg");
        Product savedProduct = productRepository.save(product);
        savedProduct.update(new ProductRequest("updateproduct", 12345, "updateproduct.jpg"));;

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
        Product product = new Product("product", 1000, "product.jpg");
        Product savedProduct = productRepository.save(product);

        productRepository.deleteById(savedProduct.getId());

        boolean exists = productRepository.existsById(savedProduct.getId());
        assertThat(exists).isFalse();
    }

}