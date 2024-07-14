package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.common.dto.PageResponse;
import gift.model.product.ProductRequest;
import gift.model.product.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql("/truncate.sql")
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("상품 등록")
    void save() {
        ProductRequest productRequest = new ProductRequest("product1", 1000, "image1.jpg");
        ProductResponse response = productService.register(productRequest);

        assertAll(
            () -> assertThat(response.id()).isNotNull(),
            () -> assertThat(response.name()).isEqualTo(productRequest.name()),
            () -> assertThat(response.price()).isEqualTo(productRequest.price()),
            () -> assertThat(response.imageUrl()).isEqualTo(productRequest.imageUrl())
        );
    }

    @Test
    @DisplayName("상품 조회")
    void findById() {
        ProductRequest productRequest = new ProductRequest("product1", 1000, "image1.jpg");
        ProductResponse response = productService.register(productRequest);
        ProductResponse product = productService.findProduct(response.id());

        assertAll(
            () -> assertThat(product.id()).isNotNull(),
            () -> assertThat(product.name()).isEqualTo(productRequest.name()),
            () -> assertThat(product.price()).isEqualTo(productRequest.price()),
            () -> assertThat(product.imageUrl()).isEqualTo(productRequest.imageUrl())
        );
    }

    @Test
    @DisplayName("전체 상품 조회")
    void findAll() {
        ProductRequest productRequest1 = new ProductRequest("product1", 1000, "image1.jpg");
        ProductRequest productRequest2 = new ProductRequest("product2", 2000, "image2.jpg");
        productService.register(productRequest1);
        productService.register(productRequest2);

        PageResponse<ProductResponse> products = productService.findAllProduct(1, 10);

        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("상품 수정")
    void update() {
        ProductRequest productRequest = new ProductRequest("product1", 1000, "image1.jpg");
        productService.register(productRequest);
        ProductRequest updateRequest = new ProductRequest("update1", 2000, "update1.jpg");
        ProductResponse response = productService.register(productRequest);

        ProductResponse product = productService.updateProduct(response.id(), updateRequest);

        assertAll(
            () -> assertThat(product.name()).isEqualTo("update1"),
            () -> assertThat(product.price()).isEqualTo(2000),
            () -> assertThat(product.imageUrl()).isEqualTo("update1.jpg")
        );
    }

    @Test
    @DisplayName("상품 삭제")
    void delete() {
        ProductRequest productRequest1 = new ProductRequest("product1", 1000, "image1.jpg");
        ProductRequest productRequest2 = new ProductRequest("product2", 2000, "image2.jpg");
        productService.register(productRequest1);
        productService.register(productRequest2);

        productService.deleteProduct(1L);
        PageResponse<ProductResponse> products = productService.findAllProduct(1, 10);

        assertThat(products.size()).isEqualTo(1);
    }
}
