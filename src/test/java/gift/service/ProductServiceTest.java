package gift.service;


import gift.domain.product.Product;
import gift.repository.product.ProductRepository;
import gift.service.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

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
        productList.add(new Product(1L, "Product 1", 1000L, "Description 1", "image1.jpg"));
        productList.add(new Product(2L, "Product 2", 2000L, "Description 2", "image2.jpg"));

        when(productRepository.findAll()).thenReturn(productList);


        List<Product> products = productService.getAllProducts();


        assertEquals(2, products.size());
        assertEquals("Product 1", products.get(0).getName());
        assertEquals("Product 2", products.get(1).getName());
    }

    @Test
    void getProductById() {

        Long productId = 1L;
        Product product = new Product(productId, "Product 1", 1000L, "Description 1", "image1.jpg");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));


        Product retrievedProduct = productService.getProductById(productId);


        assertNotNull(retrievedProduct);
        assertEquals(productId, retrievedProduct.getId());
        assertEquals("Product 1", retrievedProduct.getName());
    }


    @Test
    void getProductById_ProductNotFound() {

        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());


        assertThrows(IllegalArgumentException.class, () -> productService.getProductById(productId));
    }

    @Test
    void addProduct() {

        Product product = new Product(null, "New Product", 1500L, "New Description", "new_image.jpg");


        productService.addProduct(product);


        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void addProduct_InvalidProductName() {

        Product product = new Product(null, null, 1500L, "New Description", "new_image.jpg");


        assertThrows(IllegalArgumentException.class, () -> productService.addProduct(product));
    }

    @Test
    void updateProduct() {

        Product product = new Product(1L, "Updated Product", 2000L, "Updated Description", "updated_image.jpg");


        productService.updateProduct(product);


        verify(productRepository, times(1)).update(any(Product.class));
    }

    @Test
    void updateProduct_InvalidProductName() {

        Product product = new Product(1L, null, 2000L, "Updated Description", "updated_image.jpg");


        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(product));
    }

    @Test
    void deleteProduct() {

        Long productId = 1L;


        productService.deleteProduct(productId);


        verify(productRepository, times(1)).delete(productId);
    }
}
