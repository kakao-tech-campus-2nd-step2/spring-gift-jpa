package gift.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() throws Exception {
        jdbcTemplate.execute("DROP ALL OBJECTS");
        jdbcTemplate.execute(
            """
                CREATE TABLE product
                (
                    id       INT AUTO_INCREMENT PRIMARY KEY,
                    name     VARCHAR(255),
                    price    INT,
                    imageUrl VARCHAR(255)
                );
                """
        );
    }

    @Test
    @DisplayName("[Unit] addProduct test")
    void addProduct() {
        // given
        Product expected = new Product(-1L, "product-1", 100, "product-image-url-1");

        //when
        productRepository.addProduct(expected);
        Product actual = jdbcTemplate.queryForObject(
            "SELECT * FROM PRODUCT WHERE ID=1",
            (rs, rowNum) -> new Product(
                rs.getLong("ID"),
                rs.getString("NAME"),
                rs.getInt("PRICE"),
                rs.getString("IMAGEURL")
            )
        );

        //then
        assertAll(
            () -> assertThat(actual.id()).isEqualTo(1L),
            () -> assertThat(actual.name()).isEqualTo(expected.name()),
            () -> assertThat(actual.price()).isEqualTo(expected.price()),
            () -> assertThat(actual.imageUrl()).isEqualTo(expected.imageUrl())
        );
    }

    @Test
    @DisplayName("[Unit] getAllProducts test")
    void getAllProducts() {
        List<Product> expected = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Product product = new Product(i, "product-" + i, i * 100, "product-image-url-" + i);
            productRepository.addProduct(product);
            expected.add(product);
        }
        List<Product> actual = productRepository.getAllProducts();
        assertAll(
            () -> assertThat(actual.get(0)).isEqualTo(expected.get(0)),
            () -> assertThat(actual.get(1)).isEqualTo(expected.get(1)),
            () -> assertThat(actual.get(2)).isEqualTo(expected.get(2))
        );
    }

    @Test
    @DisplayName("[Unit] updateProduct test")
    void updateProductTest() {
        //given
        productRepository.addProduct(new Product(-1L, "product-1", 100, "product-image-url-1"));
        Product expected = new Product(1, "product-2", 200, "product-image-url-2");

        //when
        productRepository.updateProduct(expected);
        Product actual = jdbcTemplate.queryForObject(
            "SELECT * FROM PRODUCT WHERE id=1",
            (rs, rowNum) -> new Product(
                rs.getLong("ID"),
                rs.getString("NAME"),
                rs.getInt("PRICE"),
                rs.getString("IMAGEURL")
            )
        );

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("[Unit] deleteProduct test")
    void deleteProductTest() {
        //given
        productRepository.addProduct(new Product(-1L, "product-1", 100, "product-image-url-1"));

        //when
        productRepository.deleteProduct(1);
        Boolean actual = jdbcTemplate.queryForObject(
            """
                SELECT EXISTS (SELECT * FROM PRODUCT WHERE ID=1)
                """,
            Boolean.class
        );

        //then
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("[Unit] existProduct test")
    void existProduct() {
        //given
        Product expect = new Product(1L, "product-1", 100, "product-image-url-1");
        productRepository.addProduct(expect);

        //when
        Boolean trueCase = productRepository.existProduct(expect.id());
        Boolean falseCase = productRepository.existProduct(2L);

        //then
        assertAll(
            () -> assertThat(trueCase).isTrue(),
            () -> assertThat(falseCase).isFalse()
        );
    }
}