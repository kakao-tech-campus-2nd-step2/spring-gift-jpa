package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gift.domain.Product;
import gift.repository.ProductDao;
import gift.service.ProductService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductDao productDao;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product(1L, "Test Product", 99.99, "http://example.com/image.jpg");
    }

    @Test
    void testGetAllProducts() {
        when(productDao.findAll()).thenReturn(Arrays.asList(product));

        List<Product> products = productService.getAllProducts();

        assertThat(products).hasSize(1)
            .extracting(Product::getName)
            .containsExactly("Test Product");
    }

    @Test
    void testGetProductById() {
        when(productDao.findById(1L)).thenReturn(product);

        Product foundProduct = productService.getProductById(1L);

        assertThat(foundProduct.getName()).isEqualTo("Test Product");
    }

    @Test
    void testCreateProduct() {
        productService.createProduct(product);

        verify(productDao, times(1)).save(product);
    }

    @Test
    void testDeleteProduct() {
        productService.deleteProduct(1L);

        verify(productDao, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateProduct() {
        when(productDao.findById(1L)).thenReturn(product);

        Product updatedProduct = new Product(1L, "Updated Product", 149.99, "http://example.com/new_image.jpg");
        productService.updateProduct(1L, updatedProduct);

        verify(productDao, times(1)).update(1L, updatedProduct);
    }
}

