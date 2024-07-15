package gift.service;


import gift.domain.product.Product;
import gift.repository.product.ProductRepository;
import gift.service.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    void getAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Product 1", 1000L, "Description 1", "image1.jpg"));
        productList.add(new Product("Product 2", 2000L, "Description 2", "image2.jpg"));
        Page<Product> page = new PageImpl<>(productList);

        when(productRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<Product> products = productService.getAllProducts(PageRequest.of(0, 10));

        assertEquals(2, products.getTotalElements());
        assertEquals("Product 1", products.getContent().get(0).getName());
        assertEquals("Product 2", products.getContent().get(1).getName());
    }



    @Test
    void getProductById_ProductNotFound() {
        // Given
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Then
        assertThrows(IllegalArgumentException.class, () -> productService.getProductById(productId));
    }

    @Test
    void addProduct() {
        // Given
        Product product = new Product("New Product", 1500L, "New Description", "new_image.jpg");

        // When
        productService.addProduct(product);

        // Then
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void addProduct_InvalidProductName() {
        // Given
        Product product = new Product( null, 1500L, "New Description", "new_image.jpg");

        // Then
        assertThrows(IllegalArgumentException.class, () -> productService.addProduct(product));
    }

    @Test
    void updateProduct() {
        // Given
        Product product = new Product( "Updated Product", 2000L, "Updated Description", "updated_image.jpg");

        // When
        productService.updateProduct(product);

        // Then
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_InvalidProductName() {
        // Given
        Product product = new Product(null, 2000L, "Updated Description", "updated_image.jpg");

        // Then
        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(product));
    }

    @Test
    void deleteProduct() {
        // Given
        Long productId = 1L;

        // When
        productService.deleteProduct(productId);

        // Then
        verify(productRepository, times(1)).deleteById(productId);
    }
}
