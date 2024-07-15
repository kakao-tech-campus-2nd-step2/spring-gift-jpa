package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.dto.ProductDto;
import gift.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql("/sql/truncateIdentity.sql")
class ProductJpaDaoTest {

    @Autowired
    private ProductJpaDao productJpaDao;

    @Test
    @DisplayName("상품 저장 테스트")
    void save() {
        Product product = productJpaDao.save(new Product("coffee", 4500L, "https"));
        assertThat(product).isNotNull();
    }

    @Test
    @DisplayName("이름으로 상품 조회 테스트")
    void findById() {
        Product product1 = productJpaDao.save(new Product("coffee", 4500L, "https"));
        Product product2 = productJpaDao.findByName("coffee").get();
        assertThat(product1).isEqualTo(product2);
    }

    @Test
    @DisplayName("상품 모두 조회 테스트")
    void findAll() {
        productJpaDao.save(new Product("coffee", 4500L, "https"));
        productJpaDao.save(new Product("Tea", 7500L, "https"));
        productJpaDao.save(new Product("hello", 7500L, "https"));

        assertThat(productJpaDao.findAll().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void update() {
        Product product1 = new Product("Tea", 4500L, "https");
        ProductDto product2 = new ProductDto(null, "Tea", 7500L, "https");
        productJpaDao.save(product1);
        product1.updateProduct(product2);

        assertThat(productJpaDao.findByName("Tea").get().getPrice()).isEqualTo(7500L);
    }

    @Test
    @DisplayName("ID로 상품 삭제 테스트")
    void delete() {
        productJpaDao.save(new Product("coffee", 4500L, "https"));
        Long id = productJpaDao.findByName("coffee").get().getId();
        productJpaDao.deleteById(id);

        assertThat(productJpaDao.findAll().size()).isZero();
    }
}