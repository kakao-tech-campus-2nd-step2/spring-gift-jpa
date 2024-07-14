package gift.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import gift.common.exception.ProductNotFoundException;
import gift.model.product.Product;
import gift.model.product.ProductRequest;
import gift.repository.ProductRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql("/truncate.sql")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 등록")
    void save() {
        Product product = new Product(null, "상품1", 1000, "image1.jpg");

        Product actual = productRepository.save(product);

        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(product.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(product.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(product.getImageUrl())
        );
    }

    @Test
    @DisplayName("상품 조회")
    void findById() {
        Product product = productRepository.save(new Product(null, "상품1", 1000, "image1.jpg"));

        Product actual = productRepository.findById(product.getId())
            .orElseThrow(ProductNotFoundException::new);

        assertThat(actual).isEqualTo(product);
    }

    @Test
    @DisplayName("전체 상품 조회")
    void findAll() {
        productRepository.save(new Product(null, "상품1", 1000, "image1.jpg"));
        productRepository.save(new Product(null, "상품2", 2000, "image2.jpg"));

        List<Product> products = productRepository.findAll();

        assertThat(products).hasSize(2);
    }

    @Test
    @DisplayName("상품 수정")
    void update() {
        Product product = productRepository.save(new Product(null, "상품1", 1000, "image1.jpg"));

        product.updateProduct(new ProductRequest("수정된 상품", 2000, "update.jpg"));

        assertAll(
            () -> assertThat(product.getName()).isEqualTo("수정된 상품"),
            () -> assertThat(product.getPrice()).isEqualTo(2000),
            () -> assertThat(product.getImageUrl()).isEqualTo("update.jpg")
        );
    }

    @Test
    @DisplayName("상품 삭제")
    void delete() {
        productRepository.save(new Product(null, "상품1", 1000, "image1.jpg"));
        productRepository.save(new Product(null, "상품2", 2000, "image2.jpg"));

        productRepository.deleteById(1L);
        List<Product> products = productRepository.findAll();

        assertThat(products).hasSize(1);
    }
}
