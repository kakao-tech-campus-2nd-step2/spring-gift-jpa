package gift.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import gift.model.Product;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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
    @DisplayName("상품 저장 및 ID로 조회")
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
    @DisplayName("모든 상품 조회")
    public void testFindAll() {
        Product product1 = new Product(1L, "Product 1", 100, "prod1.jpg");
        Product product2 = new Product(2L, "Product 2", 200, "prod2.jpg");

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productRepository.findAll();
        assertEquals(2, products.size());
    }

    @Test
    @DisplayName("상품 삭제")
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
