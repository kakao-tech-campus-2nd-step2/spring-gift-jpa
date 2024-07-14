package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gift.dto.product.AddProductRequest;
import gift.dto.product.ProductResponse;
import gift.dto.product.UpdateProductRequest;
import gift.entity.Product;
import gift.exception.product.ProductNotFoundException;
import gift.repository.ProductRepository;
import gift.util.mapper.ProductMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

class ProductServiceTest implements AutoCloseable {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Override
    public void close() throws Exception {
        closeable.close();
    }


    @Test
    @DisplayName("getAllProducts empty test")
    @Transactional
    void getAllProductsEmptyTest() {
        //given
        Pageable pageable = Pageable.unpaged();
        when(productRepository.findAll(pageable)).thenReturn(Page.empty());

        //when
        Page<ProductResponse> products = productService.getAllProducts(pageable);

        //then
        assertThat(products).isEmpty();
    }

    @Test
    @DisplayName("getAllProducts test")
    @Transactional
    void getAllProductsTest() {
        //given
        Pageable pageable = Pageable.unpaged();
        List<Product> productList = List.of(
            Product.builder().id(1L).name("Product A").price(1000)
                .imageUrl("http://example.com/images/product_a.jpg").build(),
            Product.builder().id(2L).name("Product B").price(2000)
                .imageUrl("http://example.com/images/product_b.jpg").build(),
            Product.builder().id(3L).name("Product C").price(3000)
                .imageUrl("http://example.com/images/product_c.jpg").build(),
            Product.builder().id(4L).name("Product D").price(4000)
                .imageUrl("http://example.com/images/product_d.jpg").build(),
            Product.builder().id(5L).name("Product E").price(5000)
                .imageUrl("http://example.com/images/product_e.jpg").build()
        );
        Page<Product> productPage = new PageImpl<>(productList);
        when(productRepository.findAll(pageable)).thenReturn(productPage);

        //when
        List<ProductResponse> products = productService.getAllProducts(pageable).toList();

        //then
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(5);
        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("getProductById exception test")
    @Transactional
    void getProductByIdExceptionTest() {
        //given
        when(productRepository.findById(7L)).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> productService.getProductById(7L))
            .isInstanceOf(ProductNotFoundException.class);
        verify(productRepository, times(1)).findById(7L);
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

        when(productRepository.findById(2L)).thenReturn(Optional.of(expected));

        //when
        Product actual = productService.getProductById(2L);

        //then
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
        assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl());
        verify(productRepository, times(1)).findById(2L);
    }

    @Test
    @DisplayName("Add product test")
    void addProductTest() {
        // given
        AddProductRequest request = new AddProductRequest("Product A", 1000,
            "http://example.com/images/product_a.jpg");
        Product savedProduct = Product.builder()
            .id(1L)
            .name("Product A")
            .price(1000)
            .imageUrl("http://example.com/images/product_a.jpg")
            .build();
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // when
        Long savedId = productService.addProduct(request);

        // then
        assertThat(savedId).isNotNull();
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("updateProduct test")
    @Transactional
    void updateProductTest() {
        //given
        Product product = Product.builder()
            .id(1L)
            .name("Product A")
            .price(1000)
            .imageUrl("http://example.com/images/product_a.jpg")
            .build();
        UpdateProductRequest request = new UpdateProductRequest("product3", 30000, null);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

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
        verify(productRepository, times(2)).findById(1L);
    }

    @Test
    @DisplayName("deleteProduct test")
    @Transactional
    void deleteProductTest() {
        //given
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);

        //when
        productService.deleteProduct(1L);

        //then
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteProduct exception test")
    @Transactional
    void deleteProductExceptionTest() {
        //given
        when(productRepository.existsById(9L)).thenReturn(false);

        //when & then
        assertThatThrownBy(() -> productService.deleteProduct(9L))
            .isInstanceOf(ProductNotFoundException.class);
        verify(productRepository, times(1)).existsById(9L);
    }
}
