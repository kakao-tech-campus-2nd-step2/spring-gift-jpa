package gift.dao;

import gift.product.dao.AdminProductDao;
import gift.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private AdminProductDao productDao;

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product(
            0L,
            "상품1",
            1500,
            "product1.images"
        );
    }
    @Test
    void testRegisterProduct() {
        Product registerProduct = productDao.save(product);
        assertThat(registerProduct.getId()).isNotNull();
        assertThat(registerProduct.getName()).isEqualTo("상품1");
        assertThat(registerProduct.getPrice()).isEqualTo(1500);
        assertThat(registerProduct.getImageUrl()).isEqualTo("product1.images");
    }

}
