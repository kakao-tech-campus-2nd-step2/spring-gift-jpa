package gift.repository;

import gift.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ProductRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAndFindById() {
        Product product = new Product(null, "Test Product", 100, "test.jpg");
        Product savedProduct = new Product(1L, "Test Product", 100, "test.jpg");

        when(jdbcTemplate.update(anyString(), any(), any(), any())).thenReturn(1);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyLong())).thenReturn(List.of(savedProduct));

        productRepository.create(product);
        Optional<Product> foundProduct = productRepository.findById(1L);

        assertTrue(foundProduct.isPresent());
        assertEquals("Test Product", foundProduct.get().getName());
    }

    @Test
    public void testFindAll() {
        Product product1 = new Product(1L, "Product 1", 100, "prod1.jpg");
        Product product2 = new Product(2L, "Product 2", 200, "prod2.jpg");

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productRepository.findAll();
        assertEquals(2, products.size());
    }

    @Test
    public void testDelete() {
        Product product = new Product(1L, "Test Product", 100, "test.jpg");

        when(jdbcTemplate.update(anyString(), anyLong())).thenReturn(1);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyLong())).thenReturn(List.of());

        productRepository.create(product);
        productRepository.delete(1L);

        Optional<Product> foundProduct = productRepository.findById(1L);
        assertFalse(foundProduct.isPresent());
    }
}
