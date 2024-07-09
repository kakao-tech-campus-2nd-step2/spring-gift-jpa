package gift.repository;

import gift.model.product.Product;
import gift.model.product.ProductName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save(){
        Product expected = new Product(new ProductName("product1"),1000,"qwer.com",1000);
        productRepository.save(expected);
        Optional<Product> actual = productRepository.findById(1L);
        assertAll(
                () -> assertThat(actual.get().getId()).isNotNull(),
                () -> assertThat(actual.get().getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void delete(){
        Product expected = new Product(new ProductName("product1"),1000,"qwer.com",1000);
        productRepository.save(expected);
        productRepository.delete(expected);
        Optional<Product> actual = productRepository.findById(1L);
        assertThat(actual).isEmpty();
    }

    @Test
    void update(){
        Product expected = new Product(new ProductName("product1"),1000,"qwer.com",1000);
        Product updatedProduct = new Product(new ProductName("product2"), 1500, "updated.com", 2000);

        Product savedProduct = productRepository.save(expected);
        Long productId = savedProduct.getId();
        updatedProduct.setId(productId);

        productRepository.save(updatedProduct);

        Product fetchedProduct = productRepository.findById(productId).orElse(null);
        assertThat(fetchedProduct.getName().getName()).isEqualTo(updatedProduct.getName().getName());
    }

    @Test
    void existsByName() {
        Product expected = new Product(new ProductName("product1"),1000,"qwer.com",1000);
        Product savedProduct = productRepository.save(expected);
        assertThat(productRepository.existsById(savedProduct.getId())).isTrue();
    }

    @Test
    void purchaseProductById() {
        Product expected = new Product(new ProductName("product1"),1000,"qwer.com",1000);
        productRepository.save(expected);
        productRepository.purchaseProductById(1L, 100);
        Product savedProduct = productRepository.findById(1L).get();
        assertThat(savedProduct.getAmount()).isEqualTo(900);
    }
}