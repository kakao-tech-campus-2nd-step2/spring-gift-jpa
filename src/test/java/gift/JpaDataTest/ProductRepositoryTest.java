package gift.JpaDataTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import gift.domain.product.Product;
import gift.domain.product.repository.JpaProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
public class ProductRepositoryTest {

    @Autowired
    private JpaProductRepository productRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Description("상품 정상 저장")
    void save() {
        // given
        Product product = new Product("아이스 아메리카노 T", 4500, "https://example.com/image.jpg");
        Product savedProduct = productRepository.saveAndFlush(product);
        clear();

        // when
        Product findProduct = productRepository.findById(savedProduct.getId()).get();

        //then
        assertAll(
            () -> assertThat(findProduct.getId()).isEqualTo(savedProduct.getId()),
            () -> assertThat(findProduct.getPrice()).isEqualTo(4500),
            () -> assertThat(findProduct.getImageUrl()).isEqualTo("https://example.com/image.jpg")
        );
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
        Product product = new Product("아이스 아메리카노 T", 4500, "https://example.com/image.jpg");
        productRepository.saveAndFlush(product);
        clear();

        Product findProduct = productRepository.findByName("아이스 아메리카노 T");

        // when
        findProduct.setPrice(4700);
        findProduct.setImageUrl("https://example.com/imageModified.jpg");

        Product savedProduct = productRepository.saveAndFlush(findProduct);

        // then
        assertAll(
            () -> assertThat(savedProduct.getName()).isEqualTo("아이스 아메리카노 T"),
            () -> assertThat(savedProduct.getPrice()).isEqualTo(4700),
            () -> assertThat(savedProduct.getImageUrl()).isEqualTo(
                "https://example.com/imageModified.jpg")
        );
    }

    @Test
    @Description("상품 삭제")
    void delete() {
        //given
        Product product = new Product("아이스 아메리카노 T", 4500, "https://example.com/image.jpg");
        Product savedProduct = productRepository.saveAndFlush(product);

        // when
        productRepository.deleteById(savedProduct.getId());
        flushAndClear();

        // then
        Optional<Product> findProduct = productRepository.findById(savedProduct.getId());
        assertThat(findProduct.isPresent()).isEqualTo(false);
    }


    public void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    public void clear() {
        entityManager.clear();
    }

    public void flush() {
        entityManager.flush();
    }
}
