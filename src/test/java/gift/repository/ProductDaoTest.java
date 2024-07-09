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
class ProductDaoTest {

    private @Autowired JdbcClient jdbcClient;
    private ProductDao productDao;

    @BeforeEach
    void beforeEach() {
        productDao = new ProductDao(jdbcClient);
    }

    @Test
    @DisplayName("DB에 새 상품 삽입 테스트")
    void insertNewProduct() {
        Product newProduct = new Product(7L, "아메리카노", 4500L, "http://...");
        int result = productDao.insertNewProduct(newProduct);
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("모든 상품 리스트 가져오기 테스트")
    void selectProducts() {
        List<Product> products = productDao.selectProducts();
        assertThat(products.size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    @DisplayName("id에 해당하는 상품 가져오기 테스트")
    void selectOneProduct() {
        Product newProduct = new Product(7L, "아메리카노", 4500L, "http://...");
        productDao.insertNewProduct(newProduct);

        Product products = productDao.selectOneProduct(7L).get();
        assertThat(products.id()).isEqualTo(7L);
    }

    @Test
    @DisplayName("상품 정보 수정 테스트")
    void updateProduct() {
        Product newProduct = new Product(7L, "아메리카노", 4500L, "http://...");
        productDao.insertNewProduct(newProduct);

        Product editedProduct = new Product(7L, "아메리카노", 6500L, "http://...");
        int result = productDao.updateProduct(editedProduct);
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteProduct() {
        Product newProduct = new Product(7L, "아메리카노", 4500L, "http://...");
        productDao.insertNewProduct(newProduct);

        int result = productDao.deleteProduct(7L);
        assertThat(result).isEqualTo(1);
    }
}