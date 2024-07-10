package gift;

import gift.entity.Product;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductTest {
    @Autowired
    private ProductRepository productRepository;

    private Product testProduct;

    @BeforeEach
    public void setUp() {
        testProduct = new Product(1,1, "test", "testURL");
    }

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void testFindById() {
        productRepository.save(testProduct);
        Product foundProduct = productRepository.findById(testProduct.getId());
        assertEquals(testProduct, foundProduct);
    }

    @Test
    void testAddProduct() {
        Product savedProduct = productRepository.save(testProduct);
        assertNotNull(savedProduct);
        assertEquals(testProduct.getId(), savedProduct.getId());
    }

    @Test
    void testUpdateProduct() {
        productRepository.save(testProduct);
        var updateProduct = new Product(1, 2, "abc", "testURL");
        Product updatedProduct = productRepository.save(updateProduct);
        assertNotNull(updatedProduct);
        assertEquals(updateProduct.getId(), updatedProduct.getId());
        }
}
