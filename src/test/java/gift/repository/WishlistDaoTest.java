package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.dto.Product;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.simple.JdbcClient;

@JdbcTest
class WishlistDaoTest {

    private @Autowired JdbcClient jdbcClient;
    private WishlistDao wishlistDao;

    @BeforeEach
    void beforeEach() {
        wishlistDao = new WishlistDao(jdbcClient);
    }

    @Test
    @DisplayName("위시 리스트 상품 추가 테스트")
    void insertProduct() {
        int result = wishlistDao.insertProduct("sgoh", 7L);
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("위시 리스트 목록 가져오기 테스트")
    void findByEmail() {
        ProductDao productDao = new ProductDao(jdbcClient);

        Product product1 = new Product(7L, "아메리카노", 4500L, "http://...");
        Product product2 = new Product(8L, "아메리카노2", 4600L, "http://...");
        Product product3 = new Product(9L, "아메리카노3", 7500L, "http://...");
        productDao.insertNewProduct(product1);
        productDao.insertNewProduct(product2);
        productDao.insertNewProduct(product3);

        wishlistDao.insertProduct("sgoh", 7L);
        wishlistDao.insertProduct("sgoh", 8L);
        wishlistDao.insertProduct("sgoh", 9L);
        List<Product> wishlist = wishlistDao.findByEmail("sgoh");
        assertThat(wishlist.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("위시 리스트 상품 삭제 테스트")
    void deleteProduct() {
        wishlistDao.insertProduct("sgoh", 7L);
        int result = wishlistDao.deleteProduct("sgoh", 7L);
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("email, productId로 상품 조회 테스트")
    void findByEmailAndProductId() {
        ProductDao productDao = new ProductDao(jdbcClient);
        Product product1 = new Product(7L, "아메리카노", 4500L, "http://...");
        productDao.insertNewProduct(product1);
        wishlistDao.insertProduct("sgoh", 7L);

        Product product = wishlistDao.findByEmailAndProductId("sgoh", 7L).get();
        assertThat(product.id()).isEqualTo(7L);
    }
}