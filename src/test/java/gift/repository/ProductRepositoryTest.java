package gift.repository;

import gift.entity.Product;
import gift.entity.ProductName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void 상품_저장_조회() {
        Product product = new Product(new ProductName("오둥이 입니다만"), 29800, "https://example.com/product1.jpg");
        productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findById(product.getId());
        assertTrue(foundProduct.isPresent());
        assertEquals("오둥이 입니다만", foundProduct.get().getName().getValue());
    }

    @Test
    public void 상품_삭제() {
        Product product = new Product(new ProductName("오둥이 입니다만"), 29800, "https://example.com/product1.jpg");
        productRepository.save(product);

        productRepository.delete(product);

        Optional<Product> foundProduct = productRepository.findById(product.getId());
        assertFalse(foundProduct.isPresent());
    }
}
