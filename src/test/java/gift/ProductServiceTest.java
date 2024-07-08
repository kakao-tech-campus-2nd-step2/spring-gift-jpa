package gift;

import gift.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import gift.repository.ProductRepository;
import gift.service.ProductService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {


    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setPrice(1000);
        product1.setImageUrl("http://example.com/product1.jpg");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setPrice(2000);
        product2.setImageUrl("http://example.com/product2.jpg");

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.findAll();
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(1000);
        product.setImageUrl("http://example.com/product1.jpg");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> foundProduct = productService.findById(1L);
        assertTrue(foundProduct.isPresent());
        assertEquals(product.getId(), foundProduct.get().getId());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testSave() {
        Product product = new Product();
        product.setName("Product 1");
        product.setPrice(1000);
        product.setImageUrl("http://example.com/product1.jpg");

        Product productWithId = new Product();
        productWithId.setId(1L);
        productWithId.setName(product.getName());
        productWithId.setPrice(product.getPrice());
        productWithId.setImageUrl(product.getImageUrl());

        when(productRepository.save(any(Product.class))).thenReturn(productWithId.getPrice());

        Product savedProduct = productService.save(product);
        assertNotNull(savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testDeleteById() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }
}
