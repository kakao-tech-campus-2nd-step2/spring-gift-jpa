package gift.Service;

import gift.DTO.ProductDTO;
import gift.Entity.ProductEntity;
import gift.Repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        ProductEntity product1 = new ProductEntity(1L, "Product1", 100, "url1");
        ProductEntity product2 = new ProductEntity(2L, "Product2", 200, "url2");
        Page<ProductEntity> productPage = new PageImpl<>(Arrays.asList(product1, product2), pageable, 2);

        when(productRepository.findAll(pageable)).thenReturn(productPage);

        Page<ProductDTO> result = productService.getProducts(pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        assertEquals("Product1", result.getContent().get(0).getName());
        assertEquals("Product2", result.getContent().get(1).getName());
    }
}
