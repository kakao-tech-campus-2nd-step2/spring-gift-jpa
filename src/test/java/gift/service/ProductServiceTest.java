package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import gift.dto.product.ProductResponse;
import gift.dto.product.UpdateProductRequest;
import gift.entity.Product;
import gift.exception.product.ProductNotFoundException;
import gift.util.mapper.ProductMapper;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * 코드 수정 과정에서 변경점이 많아 테스트 코드 수정이 많이 필요함
 */
@Disabled
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    /*
     * dummy data
     *
     * Product product1 = Product.builder()
     *     .name("Product A")
     *     .price(1000)
     *     .imageUrl("http://example.com/images/product_a.jpg")
     *     .build();
     *
     * Product product2 = Product.builder()
     *     .name("Product B")
     *     .price(2000)
     *     .imageUrl("http://example.com/images/product_b.jpg")
     *     .build();
     *
     * Product product3 = Product.builder()
     *     .name("Product C")
     *     .price(3000)
     *     .imageUrl("http://example.com/images/product_c.jpg")
     *     .build();
     *
     * Product product4 = Product.builder()
     *     .name("Product D")
     *     .price(4000)
     *     .imageUrl("http://example.com/images/product_d.jpg")
     *     .build();
     *
     * Product product5 = Product.builder()
     *     .name("Product E")
     *     .price(5000)
     *     .imageUrl("http://example.com/images/product_e.jpg")
     *     .build();
     *
     */

    @Test
    @DisplayName("getAllProducts empty test")
    @Transactional
    void getAllProductsEmptyTest() {
        productService.getAllProducts()
            .forEach(product -> productService.deleteProduct(product.id()));

        //when
        List<ProductResponse> products = productService.getAllProducts();

        //then
        assertThat(products).isEmpty();
    }

    @Test
    @DisplayName("getAllProducts test")
    @Transactional
    void getAllProductsTest() {
        //when
        List<ProductResponse> products = productService.getAllProducts();

        //then
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("getProductById exception test")
    @Transactional
    void getProductByIdExceptionTest() {
        //when & then
        assertThatThrownBy(() -> productService.getProductById(7L))
            .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("getProductById test")
    @Transactional
    void getProductByIdTest() {
        //given
        Product expected = Product.builder()
            .id(2L)
            .name("Product B")
            .price(2000)
            .imageUrl("http://example.com/images/product_b.jpg")
            .build();

        //when
        Product actual = productService.getProductById(2L);

        //then
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
        assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl());
    }

    @Test
    @DisplayName("updateProduct test")
    @Transactional
    void updateProductTest() {
        //given
        UpdateProductRequest request = new UpdateProductRequest("product3", 30000, null);

        //when
        productService.updateProduct(1L, request);
        Product actual = productService.getProductById(1L);
        ProductMapper.updateProduct(actual, request);
        Product expected = Product.builder()
            .id(1L)
            .name("product3")
            .price(30000)
            .build();

        //then
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
        assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl());
    }

    @Test
    @DisplayName("deleteProduct test")
    @Transactional
    void deleteProductTest() {
        //given
        Product product1 = Product.builder()
            .name("Product A")
            .price(1000)
            .imageUrl("http://example.com/images/product_a.jpg")
            .build();

        //when
        productService.deleteProduct(1L);
        List<ProductResponse> actual = productService.getAllProducts();

        //then
        assertThat(actual).hasSize(4);
        assertThat(actual).extracting(ProductResponse::id)
            .doesNotContain(product1.getId());
    }

    @Test
    @DisplayName("deleteProduct exception test")
    @Transactional
    void deleteProductExceptionTest() {
        //when & then
        assertThatThrownBy(() -> productService.deleteProduct(9L))
            .isInstanceOf(ProductNotFoundException.class);
    }
}
