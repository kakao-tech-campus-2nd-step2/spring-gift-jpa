package gift.domain.product;

import static org.junit.jupiter.api.Assertions.*;

import gift.domain.dto.ProductRequestDto;
import gift.domain.entity.Product;
import gift.domain.repository.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProductRepositoryTest {

    private final ProductRepository productRepository;
    private Long id;
    private final ProductRequestDto reqDto;
    private final ProductRequestDto reqDto2;

    @Autowired
    ProductRepositoryTest(ProductRepository productRepository) {
        this.productRepository = productRepository;
        reqDto = new ProductRequestDto("TestName", 1000L, "http://example.com");
        reqDto2 = new ProductRequestDto("NewTestName", 2000L, "http://example.new.com");
    }

    @BeforeEach
    void setUp() {
        id = productRepository.save(reqDto).id();
    }

    @Test
    @DisplayName("ProductRepository.findAll() 테스트")
    void findAll() {
        List<Product> products = productRepository.findAll();
        assertFalse(products.isEmpty());
        assertTrue(products.stream().anyMatch(p -> p.id().equals(id)));
    }

    @Test
    @DisplayName("ProductRepository.findById() 테스트")
    void findById() {
        assertTrue(productRepository.findById(id).isPresent());
    }

    @Test
    @DisplayName("ProductRepository.findByContents() 테스트")
    void findByContents() {
        assertTrue(productRepository.findByContents(reqDto).isPresent());
    }

    @Test
    @DisplayName("ProductRepository.update() 테스트")
    void update() {
        assertEquals(
            new Product(id, reqDto2.name(), reqDto2.price(), reqDto2.imageUrl()),
            productRepository.update(id, reqDto2));
    }

    @Test
    @DisplayName("ProductRepository.save() 테스트")
    void save() {
        Product actual = productRepository.save(reqDto2);
        Product expected = ProductRequestDto.toEntity(actual.id(), reqDto2);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("ProductRepository.deleteById() 테스트")
    void deleteById() {
        productRepository.deleteById(id);
        assertTrue(productRepository.findById(id).isEmpty());
    }
}