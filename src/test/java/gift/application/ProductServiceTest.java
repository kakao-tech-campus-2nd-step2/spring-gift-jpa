package gift.application;

import gift.error.CustomException;
import gift.error.ErrorCode;
import gift.product.application.ProductService;
import gift.product.dao.ProductRepository;
import gift.product.dto.ProductRequest;
import gift.product.dto.ProductResponse;
import gift.product.entity.Product;
import gift.product.util.ProductMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 전체 조회 서비스 테스트")
    void getPagedProducts() {
        List<Product> productList = new ArrayList<>();
        Product product1 = ProductMapper.toEntity(
                new ProductRequest("product1", 1000, "https://testshop.com")
        );
        Product product2 = ProductMapper.toEntity(
                new ProductRequest("product2", 2000, "https://testshop.io")
        );
        productList.add(product1);
        productList.add(product2);

        Page<Product> productPage = new PageImpl<>(productList);

        given(productRepository.findAll(productPage.getPageable())).willReturn(productPage);

        Page<ProductResponse> responsePage = productService.getPagedProducts(productPage.getPageable());

        Assertions.assertThat(responsePage.getTotalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("상품 상세 조회 서비스 테스트")
    void getProductById() {
        Product product = new Product("product1", 1000, "https://testshop.com");
        given(productRepository.findById(any())).willReturn(Optional.of(product));

        ProductResponse resultProduct = productService.getProductByIdOrThrow(product.getId());

        Assertions.assertThat(resultProduct.id()).isEqualTo(product.getId());
    }

    @Test
    @DisplayName("상품 서비스 상세 조회 실패 테스트")
    void getProductByIdFailed() {
        Long productId = 1L;
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> productService.getProductByIdOrThrow(productId))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.PRODUCT_NOT_FOUND
                                     .getMessage());
    }

    @Test
    @DisplayName("상품 추가 서비스 테스트")
    void createProduct() {
        ProductRequest request = new ProductRequest("product1", 1000, "https://testshop.com");
        Product product = new Product("product1", 1000, "https://testshop.com");
        given(productRepository.save(any())).willReturn(product);

        ProductResponse response = productService.createProduct(request);

        Assertions.assertThat(response.id()).isEqualTo(product.getId());
    }

    @Test
    @DisplayName("단일 상품 삭제 서비스 테스트")
    void deleteProductById() {
        Product product = new Product("product1", 1000, "https://testshop.com");

        productService.deleteProductById(product.getId());

        verify(productRepository).deleteById(product.getId());
    }

    @Test
    @DisplayName("상품 전체 삭제 서비스 테스트")
    void deleteAllProducts() {

        productService.deleteAllProducts();

        verify(productRepository).deleteAll();
    }

    @Test
    @DisplayName("상품 수정 서비스 테스트")
    void updateProduct() {
        Product product = new Product("product", 1000, "https://testshop.com");
        ProductRequest request = new ProductRequest("product", 3000, "https://testshop.io");
        given(productRepository.findById(any())).willReturn(Optional.of(product));
        product.update(request);
        given(productRepository.save(any())).willReturn(product);

        Long productId = productService.updateProduct(product.getId(), request);

        Assertions.assertThat(productId).isEqualTo(product.getId());
    }

    @Test
    @DisplayName("상품 서비스 수정 실패 테스트")
    void updateProductFailed() {
        Long productId = 1L;
        ProductRequest request = new ProductRequest("product", 3000, "https://testshop.io");
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> productService.updateProduct(productId, request))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.PRODUCT_NOT_FOUND
                                     .getMessage());
    }

}