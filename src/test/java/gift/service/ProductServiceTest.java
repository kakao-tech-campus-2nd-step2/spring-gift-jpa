package gift.service;

import gift.dto.request.ProductRequest;
import gift.domain.Product;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void register() {
        ProductRequest productRequest = new ProductRequest("New Product", 100L, "new-product-url");
        Product product = new Product(1L, productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl());

        when(productRepository.findByName(productRequest.getName()))
                .thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        Product registeredProduct = productService.register(productRequest);

        assertNotNull(registeredProduct);
        assertEquals(product.getName(), registeredProduct.getName());
        assertEquals(product.getPrice(), registeredProduct.getPrice());
        assertEquals(product.getImageUrl(), registeredProduct.getImageUrl());
    }

    @Test
    void register_중복() {
        ProductRequest productRequest = new ProductRequest("Duplicate Product", 200L, "duplicate-product-url");
        Product existingProduct = new Product(1L, productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl());

        when(productRepository.findByName(productRequest.getName()))
                .thenReturn(Optional.of(existingProduct));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> productService.register(productRequest));

        assertEquals("이미 존재하는 상품입니다.", exception.getMessage());
    }

    @Test
    void findProducts() {
        Product product1 = new Product(1L, "Product 1", 100L, "url-1");
        Product product2 = new Product(2L, "Product 2", 200L, "url-2");

        when(productRepository.findAll())
                .thenReturn(Arrays.asList(product1, product2));

        List<Product> foundProducts = productService.findProducts();

        assertEquals(2, foundProducts.size());
    }

    @Test
    void findOne_id() {
        Product product = new Product(1L, "Found Product", 150L, "found-product-url");

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        Product foundProduct = productService.findOne(1L);

        assertEquals(product.getName(), foundProduct.getName());
        assertEquals(product.getPrice(), foundProduct.getPrice());
        assertEquals(product.getImageUrl(), foundProduct.getImageUrl());
    }


    @Test
    void update_id존재() {
        Long productId = 1L;
        ProductRequest updatedProductRequest = new ProductRequest("Updated Product", 300L, "updated-product-url");
        Product updatedProduct = new Product(productId, updatedProductRequest.getName(), updatedProductRequest.getPrice(), updatedProductRequest.getImageUrl());

        when(productRepository.updateById(productId, updatedProductRequest))
                .thenReturn(Optional.of(updatedProduct));

        Product result = productService.update(productId, updatedProductRequest);

        assertNotNull(result);
        assertEquals(updatedProduct.getName(), result.getName());
        assertEquals(updatedProduct.getPrice(), result.getPrice());
        assertEquals(updatedProduct.getImageUrl(), result.getImageUrl());
    }

    @Test
    void update_id존재x() {
        Long productId = 999L;
        ProductRequest updatedProductRequest = new ProductRequest("Updated Product", 300L, "updated-product-url");

        when(productRepository.updateById(productId, updatedProductRequest))
                .thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> productService.update(productId, updatedProductRequest));

        assertEquals("존재하지 않는 상품입니다.", exception.getMessage());
    }

    @Test
    void delete_id() {

        Long productId = 1L;
        Product product = new Product(productId, "To Be Deleted", 200L, "delete-me-url");

        when(productRepository.deleteById(productId))
                .thenReturn(Optional.of(product));

        Product result = productService.delete(productId);

        assertEquals(product.getName(), result.getName());
        assertEquals(product.getPrice(), result.getPrice());
        assertEquals(product.getImageUrl(), result.getImageUrl());
    }

}
