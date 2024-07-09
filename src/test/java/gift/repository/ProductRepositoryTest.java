package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.entity.Product;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("ProductDao의 insertProduct 메서드에 대응")
    void save() {
        Product expected = new Product("커피", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
        Product actual = productRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo("커피"),
                () -> assertThat(actual.getPrice()).isEqualTo(10000),
                () -> assertThat(actual.getImageUrl()).isNotNull()
        );
    }

    @Test
    @DisplayName("ProductDao의 selectProduct 메서드에 대응")
    void findAll(){
        Product product1 = new Product("커피1", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
        Product product2 = new Product("커피2", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

        productRepository.save(product1);
        productRepository.save(product2);

        List<Product> actualList = productRepository.findAll();
        assertThat(actualList).containsExactlyInAnyOrder(product1, product2);
    }

    @Test
    @DisplayName("ProductDao의 selectOneProduct에 대응")
    void findOne(){
        Product product1 = new Product("커피1", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
        Product product2 = new Product("커피2", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

        productRepository.save(product1);
        productRepository.save(product2);

        Product actual = productRepository.findItemById(product2.getId());

        assertThat(actual).isEqualTo(product2);
    }

    @Test
    @DisplayName("ProductDao의 updateProduct에 대응, 변경감지")
    void update(){
        Product product1 = new Product("커피1", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
        Product product2 = new Product("커피2", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

        productRepository.save(product1);
        productRepository.save(product2);

        Product expected = productRepository.findItemById(product2.getId());

        expected.setPrice(31240941);

        Product actual = productRepository.findItemById(product2.getId());

        assertThat(actual).isEqualTo(expected);

    }



}

