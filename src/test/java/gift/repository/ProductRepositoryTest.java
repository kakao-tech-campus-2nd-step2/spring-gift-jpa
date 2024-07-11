package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.entity.Product;
import gift.exception.product.ProductNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        entityManager.getEntityManager()
            .createNativeQuery("ALTER TABLE products ALTER COLUMN id RESTART WITH 1")
            .executeUpdate();
        insertion();
    }

    void insertion() {
        Product product1 = Product.builder()
            .name("Product A")
            .price(1000)
            .imageUrl("http://example.com/images/product_a.jpg")
            .build();

        Product product2 = Product.builder()
            .name("Product B")
            .price(2000)
            .imageUrl("http://example.com/images/product_b.jpg")
            .build();

        Product product3 = Product.builder()
            .name("Product C")
            .price(3000)
            .imageUrl("http://example.com/images/product_c.jpg")
            .build();

        Product product4 = Product.builder()
            .name("Product D")
            .price(4000)
            .imageUrl("http://example.com/images/product_d.jpg")
            .build();

        Product product5 = Product.builder()
            .name("Product E")
            .price(5000)
            .imageUrl("http://example.com/images/product_e.jpg")
            .build();

        productRepository.saveAll(List.of(product1, product2, product3, product4, product5));
    }

    @Test
    @DisplayName("find all test")
    void findAllTest() {
        // when
        List<Product> products = productRepository.findAll();

        // then
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("find by id test")
    void findByIdTest() {
        // when
        final Product actual = productRepository.findById(1L).get();
        Product expected = Product.builder()
            .id(1L)
            .name("Product A")
            .price(1000)
            .imageUrl("http://example.com/images/product_a.jpg")
            .build();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
        assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl());
    }

    @Test
    @DisplayName("find by not exist id test")
    void findByNotExistIdTest() {
        // when
        final Optional<Product> actual = productRepository.findById(10L);

        // then
        assertThat(actual).isNotPresent();
    }

    @Test
    @DisplayName("create test")
    void createTest() {
        // given
        Product newProduct = Product.builder()
            .name("new product")
            .price(10_000)
            .build();

        // when
        final Product actual = productRepository.save(newProduct);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo("new product");
        assertThat(actual.getPrice()).isEqualTo(10_000);
        assertThat(actual.getImageUrl()).isNull();
    }

    @Test
    @DisplayName("update test")
    void updateTest() {
        // given
        final Product updateProduct = productRepository.findById(1L)
            .orElseThrow(() -> new ProductNotFoundException(""));

        // when
        updateProduct.changeName("update product");
        final Product actual = productRepository.save(updateProduct);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getName()).isEqualTo("update product");
        assertThat(actual.getPrice()).isEqualTo(1_000);
        assertThat(actual.getImageUrl()).isEqualTo("http://example.com/images/product_a.jpg");
    }

    @Test
    @DisplayName("delete test")
    void deleteTest() {
        // when
        productRepository.deleteById(1L);
        List<Product> products = productRepository.findAll();
        Optional<Product> deletedProduct = productRepository.findById(1L);

        // then
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(4);
        assertThat(deletedProduct).isNotPresent();
    }
}
