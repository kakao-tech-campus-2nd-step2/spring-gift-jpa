package gift.JpaDataTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import gift.domain.product.Product;
import gift.domain.product.ProductService;
import gift.domain.product.JpaProductRepository;
import gift.global.exception.BusinessException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProductRepositoryTest {

    @Autowired
    private JpaProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @PersistenceContext
    private EntityManager entityManager;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        Product product1 = new Product("아이스 아메리카노 T", 4500, "https://example.com/image.jpg");
        this.product1 = product1;
        Product product2 = new Product("아이스 말차라떼 T", 4500, "https://example.com/image.jpg");
        this.product2 = product2;
    }

    @Test
    @Description("상품 정상 저장")
    void save() {
        // when
        Product savedProduct = productRepository.saveAndFlush(product1);
        clear();
        Product findProduct = productRepository.findById(savedProduct.getId()).get();

        //then
        assertThat(savedProduct).isEqualTo(findProduct);
    }

    @Test
    @Description("카카오 문구 포함 상품 저장 실패")
    void kakaoPersistFailed() {
        // given
        Product product = new Product("아이스 카카오 라떼 T", 4500, "https://example.com/image.jpg");

        // when, then
        assertThrows(ConstraintViolationException.class, () -> productRepository.saveAndFlush(product));
    }

    @Test
    @Description("카카오 문구 포함 상품 검증 메서드")
    void kakaoValidation() {
        // given
        Product product = new Product("아이스 카카오 라떼 T", 4500, "https://example.com/image.jpg");

        // when, then
        assertThrows(BusinessException.class, () -> productService.validateProduct(product));
    }

    @Test
    @Description("상품 저장 시 이름 중복 검증")
    void saveWithSameName() {
        // given
        Product product1 = new Product("아이스 아메리카노 T", 4500, "https://example.com/image.jpg");
        Product product2 = new Product("아이스 아메리카노 T", 4700, "https://example.com/image.jpg");

        // when
        productRepository.save(product1);

        /// then
        assertThrows(DataIntegrityViolationException.class,
            () -> productRepository.saveAndFlush(product2));
    }


    @Test
    @Description("상품 수정")
    void update() {
        // given
        productRepository.saveAndFlush(product1);
        clear();
        Product findProduct = productRepository.findById(product1.getId()).get();

        // when
        findProduct.setPrice(4700);
        findProduct.setImageUrl("https://example.com/imageModified.jpg");

        Product savedProduct = productRepository.saveAndFlush(findProduct);

        // then
        assertThat(findProduct).isEqualTo(savedProduct);
    }

    @Test
    @Description("상품 삭제")
    void delete() {
            // when
            Product savedProduct = productRepository.saveAndFlush(product1);
            productRepository.deleteById(savedProduct.getId());

            // then
            Optional<Product> findProduct = productRepository.findById(savedProduct.getId());
            assertThat(findProduct.isEmpty()).isEqualTo(true);
    }

    @Test
    @Description("상품들 삭제")
    void deleteAllByIdsIn() {
        // given
        Product savedProduct1 = productRepository.saveAndFlush(product1);
        Product savedProduct2 = productRepository.saveAndFlush(product2);
        List<Long> ids = new ArrayList<>();
        ids.add(savedProduct1.getId());
        ids.add(savedProduct2.getId());

        // when
        productRepository.deleteAllByIdIn(ids);
        List<Product> products = productRepository.findAll();
        // then
        assertThat(products.size()).isEqualTo(0);
    }

    private void flush() {
        entityManager.flush();
    }
    private void clear() {
        entityManager.clear();
    }

}
