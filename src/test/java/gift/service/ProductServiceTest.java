package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import gift.domain.product.dto.ProductRequest;
import gift.domain.product.dto.ProductResponse;
import gift.domain.product.entity.Product;
import gift.domain.product.repository.ProductRepository;
import gift.domain.product.service.ProductService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService productService;
    @Mock
    ProductRepository productRepository;

    @Test
    @DisplayName("Id로 Product 조회 테스트")
    void getProduct() {
        // given
        Long id = 1L;
        Product expected = new Product(1L, "test", 1000, "test.jpg");

        doReturn(Optional.of(expected)).when(productRepository).findById(id);

        // when
        ProductResponse actual = productService.getProduct(id);

        // then
        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    @DisplayName("모든 Product 조회 테스트")
    void getAllProducts() {
        // given
        Product product1 = new Product(1L, "test1", 1000, "test1.jpg");
        Product product2 = new Product(2L, "test2", 2000, "test2.jpg");

        List<Product> productList = Arrays.asList(product1, product2);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> pageList = new PageImpl<>(productList, pageable, productList.size());

        List<ProductResponse> expected = Arrays.asList(entityToDto(product1),
            entityToDto(product2));

        doReturn(pageList).when(productRepository).findAll(pageable);

        // when
        List<ProductResponse> actual = productService.getAllProducts(pageable.getPageNumber(),
            pageable.getPageSize());

        // then
        assertAll(
            () -> assertThat(actual).isNotNull(),
            () -> assertThat(actual).hasSize(2),
            () -> {
                for (int i = 0; i < expected.size(); i++) {
                    final int index = i;
                    assertAll(
                        () -> assertThat(actual.get(index).getName()).isEqualTo(
                            expected.get(index).getName()),
                        () -> assertThat(actual.get(index).getPrice()).isEqualTo(
                            expected.get(index).getPrice()),
                        () -> assertThat(actual.get(index).getImageUrl()).isEqualTo(
                            expected.get(index).getImageUrl())
                    );
                }
            }
        );

    }

    @Test
    @DisplayName("product 저장 테스트")
    void create() {
        // given
        ProductRequest productRequest = new ProductRequest("test", 1000, "test.jpg");
        Product savedProduct = new Product(productRequest.getName(), productRequest.getPrice(),
            productRequest.getImageUrl());
        ProductResponse expected = new ProductResponse(savedProduct.getName(),
            savedProduct.getPrice(), savedProduct.getImageUrl());

        doReturn(savedProduct).when(productRepository).save(any(Product.class));

        // when
        ProductResponse actual = productService.addProduct(productRequest);

        // then
        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    @DisplayName("product 업데이트 테스트")
    void updateProduct() {
        // given
        Long id = 1L;
        ProductRequest productRequest = new ProductRequest("update", 1000, "update.jpg");
        Product savedProduct = mock(Product.class);
        Product updatedProduct = new Product("update", 1000, "update.jpg");

        ProductResponse expected = new ProductResponse(updatedProduct.getName(),
            updatedProduct.getPrice(), updatedProduct.getImageUrl());

        doReturn(Optional.of(savedProduct)).when(productRepository).findById(id);
        doNothing().when(savedProduct).update(productRequest.getName(), productRequest.getPrice(),
            productRequest.getImageUrl());
        doReturn(updatedProduct).when(productRepository).save(any(Product.class));

        // when
        ProductResponse actual = productService.updateProduct(id, productRequest);

        // then
        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    @DisplayName("product 삭제 테스트")
    void delete() {
        // given
        Long id = 1L;
        Product savedProduct = new Product("test", 1000, "test.jpg");

        doReturn(Optional.of(savedProduct)).when(productRepository).findById(id);

        // when
        productService.deleteProduct(id);

        // then
        verify(productRepository, times(1)).delete(savedProduct);
    }

    private ProductResponse entityToDto(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl());
    }
}