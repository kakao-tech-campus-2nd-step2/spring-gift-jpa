package gift.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.product.model.Product;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product("Product1", 1000, "http://example.com/image1.jpg");
        product2 = new Product("Product2", 2000, "http://example.com/image2.jpg");
        productRepository.save(product1);
        productRepository.save(product2);
    }

    @Test
    void testFindById() {
        Optional<Product> product = productRepository.findById(product1.getId());
        assertThat(product).isPresent();
        assertThat(product.get().getName()).isEqualTo("Product1");
    }

    @Test
    void testFindAll() {
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(2);
    }

    @Test
    void testSaveNewProduct () {
        Product newProduct = new Product("Product3", 3000, "http://example.com/image3.jpg");
        Product savedProduct = productRepository.save(newProduct);

        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Product3");
    }

    @Test
    void testSaveUpdateProduct() {
        product1.setName("UpdateProduct");
        productRepository.save(product1);

        Optional<Product> updateProduct = productRepository.findById(product1.getId());
        assertThat(updateProduct).isPresent();
        assertThat(updateProduct.get().getName()).isEqualTo("UpdateProduct");
    }

    @Test
    void testDeleteById() {
        productRepository.delete(product2);
        List<Product> product = productRepository.findAll();
        assertThat(product).hasSize(1);
    }
}