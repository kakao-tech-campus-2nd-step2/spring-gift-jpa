package gift.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import gift.product.model.ProductRepository;
import gift.product.model.dto.Product;
import gift.user.model.dto.Role;
import gift.user.model.dto.User;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
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
    private User user;
    private Product product;

    @BeforeEach
    public void setUp() {
        user = new User("aabb@kakao.com", "1234", Role.SELLER, "aaaa");
        product = new Product("Test", 1000, "url", user);
    }

    @Test
    public void testFindActiveProductById() {
        Product foundProduct = productRepository.findByIdAndIsActiveTrue(product.getId())
                .orElseThrow(() -> new EntityNotFoundException("Product"));
        assertEquals(product.getName(), foundProduct.getName());
    }

    @Test
    public void testFindAllByIsActiveTrue() {
        List<Product> products = productRepository.findAllByIsActiveTrue();
        assertTrue(products.stream().allMatch(Product::isActive));
    }

    @Test
    public void testSaveAndDelete() {
        Product savedProduct = productRepository.save(product);

        productRepository.delete(savedProduct);
        Optional<Product> result = productRepository.findById(savedProduct.getId());
        assertFalse(result.isPresent());
    }
}
