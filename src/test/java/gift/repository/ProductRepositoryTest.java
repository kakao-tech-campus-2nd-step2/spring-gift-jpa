package gift.repository;

import gift.vo.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;
    Product savedProduct;

    @BeforeEach
    void setUp() {
        Product product = new Product("Ice Americano",
                4200,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
        savedProduct = repository.save(product); // Product pre-save
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll(); // Clean up after each test
    }

    @Test
    @DisplayName("Product save")
    void save() {
        // given
        Product newProduct = new Product("Ice Americano",
                4200,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
        // when
        Product actual = repository.save(newProduct);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(newProduct.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(newProduct.getPrice()),
                () -> assertThat(actual.getImageUrl()).isEqualTo(newProduct.getImageUrl())
        );
    }

    @Test
    @DisplayName("Find All Product")
    void findAllProduct() {
        // given
        Product product2 = repository.save(new Product("Ice Americano",
                4200,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"));
        // when
        List<Product> products = repository.findAll();

        // then
        assertAll(
                () ->  assertThat(products.size()).isEqualTo(2),
                () -> assertThat(products.contains(savedProduct)).isTrue(),
                () -> assertThat(products.contains(product2)).isTrue()
        );
    }

    @Test
    @DisplayName("Find Product by id")
    void findProductById() {
        // given
        Long expected = savedProduct.getId();

        // when
        Optional<Product> actual = repository.findById(expected);

        // then
        assertTrue(actual.isPresent());
        assertThat(actual.get().getId()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Delete Product by id")
    void deleteProductById() {
        // when
        repository.deleteById(savedProduct.getId());
        boolean exists = repository.existsById(savedProduct.getId());

        // then
        assertThat(exists).isFalse();
    }

}