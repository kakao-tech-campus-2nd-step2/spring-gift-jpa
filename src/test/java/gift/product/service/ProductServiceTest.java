package gift.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gift.product.domain.Product;
import gift.product.exception.ProductNotFoundException;
import gift.product.persistence.ProductRepository;
import gift.product.service.dto.ProductParam;
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
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("ProductService 생성 테스트[성공]")
    void saveProductTest() {
        //given
        ProductParam productParam = new ProductParam("테스트 상품", 1000, "http://test.com");
        Product savedProduct = new Product(1L, "테스트 상품", 1000, "http://test.com");

        //when
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        Long savedProductId = productService.saveProduct(productParam);

        //then
        verify(productRepository).save(any(Product.class));
        assertThat(savedProductId).isEqualTo(1L);
    }

    @Test
    @DisplayName("ProductService 수정 테스트[성공]")
    void modifyProductTest() {
        //given
        Long id = 1L;
        ProductParam productParam = new ProductParam("수정된 상품", 2000, "http://test.com");
        Product product = new Product(1L, "테스트 상품", 1000, "http://test.com");

        //when
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        productService.modifyProduct(id, productParam);

        //then
        verify(productRepository).findById(id);
        assertThat(product.getName()).isEqualTo("수정된 상품");
        assertThat(product.getPrice()).isEqualTo(2000);
        assertThat(product.getImgUrl()).isEqualTo("http://test.com");
    }

    @Test
    @DisplayName("ProductService 삭제 테스트[성공]")
    void deleteProductTest() {
        //given
        final Long id = 1L;
        Product product = new Product(id, "테스트 상품", 1000, "http://test.com");

        //when
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        productService.deleteProduct(id);

        //then
        verify(productRepository).findById(id);
        verify(productRepository).delete(product);
    }

    @Test
    @DisplayName("ProductService 조회 테스트[성공]")
    void getProductDetailsTest() {
        //given
        final Long id = 1L;
        Product product = new Product(id, "테스트 상품", 1000, "http://test.com");

        //when
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        var productInfo = productService.getProductDetails(id);

        //then
        verify(productRepository).findById(id);
        assertThat(productInfo.id()).isEqualTo(1L);
        assertThat(productInfo.name()).isEqualTo("테스트 상품");
        assertThat(productInfo.price()).isEqualTo(1000);
        assertThat(productInfo.imgUrl()).isEqualTo("http://test.com");
    }

    @Test
    @DisplayName("ProductService 조회 테스트[실패]")
    void getProductDetailsFailTest() {
        //given
        final Long id = 1L;

        //when
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> productService.getProductDetails(id))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("ProductService 목록 조회 테스트")
    void getProductsTest() {
        //given
        Page<Product> productPage = new PageImpl<>(List.of(
                new Product(1L, "테스트 상품1", 1000, "http://test.com"),
                new Product(2L, "테스트 상품2", 2000, "http://test.com")
        ));
        Pageable pageable = Pageable.ofSize(10).first();

        //when
        when(productRepository.findAll(pageable)).thenReturn(productPage);
        var products = productService.getProducts(pageable);

        //then
        verify(productRepository).findAll(pageable);
        assertThat(products.products().size()).isEqualTo(2);
        assertThat(products.currentPage()).isEqualTo(1);
        assertThat(products.totalPages()).isEqualTo(1);
        assertThat(products.totalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("ProductService 수정 테스트[실패]")
    void modifyProductFailTest() {
        //given
        final Long id = 1L;
        ProductParam productParam = new ProductParam("수정된 상품", 2000, "http://test.com");

        //when
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> productService.modifyProduct(id, productParam))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("ProductService 삭제 테스트[실패]")
    void deleteProductFailTest() {
        //given
        final Long id = 1L;

        //when
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> productService.deleteProduct(id))
                .isInstanceOf(ProductNotFoundException.class);
    }
}