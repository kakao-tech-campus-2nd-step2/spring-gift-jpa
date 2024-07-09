package gift.dao;

import gift.product.dao.ProductDao;
import gift.product.model.Product;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductDao productDao;

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product(
            1L,
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

        System.out.println("삽입된 객체의 ID = " + registerProduct.getId());
    }

    @Test
    void testSearchProductById() {
        Product savedProduct = productDao.save(product);

        Optional<Product> retrievedProduct = productDao.findById(1L);
        System.out.println("삽입된 객체의 ID = " + savedProduct.getId());

        assertThat(retrievedProduct).isPresent();
        assertAll(
            () -> assertThat(retrievedProduct.get().getId()).isEqualTo(savedProduct.getId()),
            () -> assertThat(retrievedProduct.get().getName()).isEqualTo("상품1"),
            () -> assertThat(retrievedProduct.get().getPrice()).isEqualTo(1500),
            () -> assertThat(retrievedProduct.get().getImageUrl()).isEqualTo("product1.images")
        );
    }

    @Test
    void testUpdateProduct() {
        Product registerProduct = productDao.save(product);

        Optional<Product> saveProduct = productDao.findById(registerProduct.getId());

        assertThat(saveProduct).isPresent();
        assertThat(saveProduct.get().getPrice()).isEqualTo(1500);

        productDao.save(
            new Product(
                saveProduct.get().getId(),
                saveProduct.get().getName(),
                2000,
                saveProduct.get().getImageUrl()
            )
        );

        Optional<Product> updateProduct = productDao.findById(saveProduct.get().getId());

        assertThat(updateProduct).isPresent();
        assertThat(updateProduct.get().getPrice()).isEqualTo(2000);
    }

    @Test
    void testDeleteProduct() {
        Product savedProduct = productDao.save(product);

        Optional<Product> retrievedProduct = productDao.findById(savedProduct.getId());
        assertThat(retrievedProduct).isPresent();

        productDao.deleteById(savedProduct.getId());

        Optional<Product> deletedProduct = productDao.findById(savedProduct.getId());
        assertThat(deletedProduct).isNotPresent();
    }
}
