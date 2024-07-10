package gift.doamin.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.doamin.product.entity.Product;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class JpaProductRepositoryTest {
    @Autowired
    private JpaProductRepository jpaProductRepository;

    @Test
    void save() {
        Product product = new Product(1L, "test", 10000, "test.png");

        Product savedProduct = jpaProductRepository.save(product);

        assertThat(savedProduct.getId()).isNotNull();
    }

    @Test
    void findAll() {
        Product product1 = new Product(1L, "test1", 1, "test1.png");
        jpaProductRepository.save(product1);
        Product product2 = new Product(2L, "test2", 2, "test2.png");
        jpaProductRepository.save(product2);
        
        List<Product> allProducts = jpaProductRepository.findAll();

        assertThat(allProducts.size()).isEqualTo(2);
    }

    @Test
    void findById() {
        Product product = new Product(1L, "test", 10000, "test.png");
        Product savedProduct = jpaProductRepository.save(product);

        Optional<Product> foundProduct = jpaProductRepository.findById(savedProduct.getId());

        assertThat(foundProduct.get()).isEqualTo(savedProduct);
    }

    @Test
    void deleteById() {
        Product product = new Product(1L, "test", 10000, "test.png");
        Product savedProduct = jpaProductRepository.save(product);

        jpaProductRepository.deleteById(savedProduct.getId());
        Optional<Product> foundProduct = jpaProductRepository.findById(savedProduct.getId());

        assertThat(foundProduct.isEmpty()).isTrue();
    }

}