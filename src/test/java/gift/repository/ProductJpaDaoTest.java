package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductJpaDaoTest {

    @Autowired
    private ProductJpaDao productJpaDao;

    @Test
    @DisplayName("상품 저장 테스트")
    void save() {
        Product product = productJpaDao.save(new Product(7L, "coffee", 4500L, "https"));
        assertThat(product).isNotNull();
    }

    @Test
    @DisplayName("ID로 상품 조회 테스트")
    void findById() {
        Product product1 = productJpaDao.save(new Product(7L, "coffee", 4500L, "https"));
        Product product2 = productJpaDao.findById(7L).get();
        assertThat(product1).isEqualTo(product2);
    }

    @Test
    @DisplayName("상품 모두 조회 테스트")
    void findAll() {
        productJpaDao.save(new Product(7L, "coffee", 4500L, "https"));
        productJpaDao.save(new Product(8L, "Tea", 7500L, "https"));
        productJpaDao.save(new Product(9L, "hello", 7500L, "https"));

        assertThat(productJpaDao.findAll().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void update() {
        Product product1 = new Product(7L, "coffee", 4500L, "https");
        Product product2 = new Product(7L, "Tea", 7500L, "https");
        productJpaDao.save(product1);
        productJpaDao.save(product2);

        assertThat(productJpaDao.findById(7L).get().getName()).isEqualTo("Tea");
    }

    @Test
    @DisplayName("ID로 상품 삭제 테스트")
    void delete() {
        productJpaDao.save(new Product(7L, "coffee", 4500L, "https"));
        productJpaDao.deleteById(7L);

        assertThat(productJpaDao.findAll().size()).isZero();
    }
}