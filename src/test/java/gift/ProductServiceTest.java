package gift;

import gift.model.Product;
import gift.repository.ProductRepository;
import gift.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        // 제품 저장
        Product product = Product.builder()
                .id(1L)
                .name("Product 1")
                .price(1000)
                .imageurl("https://t1.kakaocdn.net/kakaocorp/kakaocorp/admin/5f9c58c2017800001.png")
                .build();

        productService.save(product);

        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testGetAllProducts() {
        // 모든 제품 조회
        List<Product> products = new ArrayList<>();
        products.add(Product.builder()
                .id(1L)
                .name("Product 1")
                .price(1000)
                .imageurl("https://t1.kakaocdn.net/kakaocorp/kakaocorp/admin/5f9c58c2017800001.png")
                .build());
        products.add(Product.builder()
                .id(2L)
                .name("Product 2")
                .price(2000)
                .imageurl("https://t1.kakaocdn.net/thumb/R1920x0.fwebp.q100/?fname=https%3A%2F%2Ft1.kakaocdn.net%2Fkakaocorp%2Fkakaocorp%2Fadmin%2Fservice%2Fa85d0594017900001.jpg")
                .build());

        when(productRepository.findAll()).thenReturn(products);

        ResponseEntity<List<Product>> response = productService.getAllProducts();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void testUpdate() {
        // 제품 업데이트
        Product updatedProduct = Product.builder()
                .id(1L)
                .name("Updated Product")
                .price(1500)
                .imageurl("https://i.namu.wiki/i/e-zj0gvlG57yiCzl62eIHWIAEOQqyV5CZHDbJjxfbu2Nj_-BTb7r0nZJdRl3g4Sfvbp_EyvlWEcyezhOqr-zPg.webp")
                .build();

        productService.update(updatedProduct);

        verify(productRepository, times(1)).save(updatedProduct);
    }

    @Test
    void testFindById() {
        // ID로 제품 조회
        Long id = 1L;
        Product product = Product.builder()
                .id(id)
                .name("Product 1")
                .price(1000)
                .imageurl("https://t1.kakaocdn.net/kakaocorp/kakaocorp/admin/5f9c58c2017800001.png")
                .build();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product foundProduct = productService.findById(id);

        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isEqualTo(id);
    }

    @Test
    void testFindByIdReturnsNullWhenNotFound() {
        // 존재하지 않는 ID로 제품 조회시 Null
        Long id = 1L;

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        Product foundProduct = productService.findById(id);

        assertThat(foundProduct).isNull();
    }

    @Test
    void testDelete() {
        // 제품 삭제
        Long id = 1L;

        doNothing().when(productRepository).deleteById(id);

        productService.delete(id);

        verify(productRepository, times(1)).deleteById(id);
    }
}