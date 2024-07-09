package gift.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import gift.product.model.ProductRepository;
import gift.product.model.dto.Product;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@EntityScan(basePackages = "gift.product.model")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testFindActiveProductById() {
        // 테스트 데이터 세팅
        Product newProduct = new Product();
        newProduct.setName("Test");
        newProduct.setPrice(1000);
        newProduct.setImageUrl("url");
        newProduct.setActive(true);
        productRepository.save(newProduct);

        // 제품 검색
        Product foundProduct = productRepository.findActiveProductById(newProduct.getId());
        assertNotNull(foundProduct);
        assertEquals("Test", foundProduct.getName());
    }

    @Test
    public void testFindAllByIsActiveTrue() {
        // 활성 상태의 제품들만 반환하는지 테스트
        List<Product> products = productRepository.findAllByIsActiveTrue();
        assertTrue(products.stream().allMatch(Product::isActive));
    }

    @Test
    public void testSaveAndDelete() {
        // 제품 저장 후 삭제 테스트
        Product product = new Product();
        product.setName("Delete");
        product.setPrice(2000);
        product.setActive(true);
        Product savedProduct = productRepository.save(product);

        productRepository.delete(savedProduct);
        Optional<Product> result = productRepository.findById(savedProduct.getId());
        assertFalse(result.isPresent());
    }
}
