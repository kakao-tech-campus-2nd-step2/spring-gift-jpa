package gift.repository;

import gift.dto.request.ProductRequest;
import gift.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class ProductDBRepositoryTest {

    private ProductDBRepository repository;

    @Mock
    private DataSource dataSource;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        repository = new ProductDBRepository(dataSource);
    }

    @Test
    void saveProduct() {
        Product product = new Product("Test Product", 100, "test-url");

        when(jdbcTemplate.update(anyString(), any(), any(), any())).thenReturn(1);

        Product savedProduct = repository.save(product);

        assertNotNull(savedProduct);
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getPrice(), savedProduct.getPrice());
        assertEquals(product.getImageUrl(), savedProduct.getImageUrl());
    }

    @Test
    void findById_id있을때() {
        Product product = new Product(1L, "Test Product", 100, "test-url");

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyLong()))
                .thenReturn(Arrays.asList(product));

        Optional<Product> foundProduct = repository.findById(1L);

        assertTrue(foundProduct.isPresent());
        assertEquals(product, foundProduct.get());
    }

    @Test
    void findById_id없을때() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyLong()))
                .thenReturn(Arrays.asList());

        Optional<Product> foundProduct = repository.findById(999L);

        assertFalse(foundProduct.isPresent());
    }

    @Test
    void findByName_id있을때() {
        Product product = new Product(1L, "Test Product", 100, "test-url");

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyString()))
                .thenReturn(Arrays.asList(product));

        Optional<Product> foundProduct = repository.findByName("Test Product");

        assertTrue(foundProduct.isPresent());
        assertEquals(product, foundProduct.get());
    }

    @Test
    void findByName_id없을때() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyString()))
                .thenReturn(Arrays.asList());

        Optional<Product> foundProduct = repository.findByName("Non Existing Product");

        assertFalse(foundProduct.isPresent());
    }

    @Test
    void findAll_returnsAllProducts() {
        Product product1 = new Product(1L, "Product 1", 100, "url-1");
        Product product2 = new Product(2L, "Product 2", 200, "url-2");

        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(Arrays.asList(product1, product2));

        List<Product> allProducts = repository.findAll();

        assertEquals(2, allProducts.size());
    }

    @Test
    void updateById_id있을때() {
        ProductRequest updatedProductRequest = new ProductRequest("Updated Product", 200, "updated-url");

        when(jdbcTemplate.update(anyString(), any(), any(), any(), any()))
                .thenReturn(1);

        Optional<Product> updatedProductOptional = repository.updateById(1L, updatedProductRequest);

        assertTrue(updatedProductOptional.isPresent());
        Product updatedProduct = updatedProductOptional.get();
        assertEquals(updatedProductRequest.getName(), updatedProduct.getName());
        assertEquals(updatedProductRequest.getPrice(), updatedProduct.getPrice());
        assertEquals(updatedProductRequest.getImageUrl(), updatedProduct.getImageUrl());
    }

    @Test
    void updateById_id없을때() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any()))
                .thenReturn(0);

        Optional<Product> updatedProductOptional = repository.updateById(999L, new ProductRequest("Updated Product", 200, "updated-url"));

        assertFalse(updatedProductOptional.isPresent());
    }

    @Test
    void deleteById_id있을때() {
        Product product = new Product(1L, "To Be Deleted", 100, "delete-me-url");

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyLong()))
                .thenReturn(Arrays.asList(product));

        Optional<Product> deletedProductOptional = repository.deleteById(1L);

        assertTrue(deletedProductOptional.isPresent());
        assertEquals(product, deletedProductOptional.get());
    }

    @Test
    void deleteById_id없을때() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyLong()))
                .thenReturn(Arrays.asList());

        Optional<Product> deletedProductOptional = repository.deleteById(999L);

        assertFalse(deletedProductOptional.isPresent());
    }
}
