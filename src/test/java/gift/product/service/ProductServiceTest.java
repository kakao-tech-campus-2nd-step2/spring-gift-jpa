package gift.product.service;

import static org.assertj.core.api.Assertions.assertThat;

import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import gift.product.validator.ProductNameValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
class ProductServiceTest {
    @Autowired
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, new ProductNameValidator());

        Product product1 = new Product("Product 1", 100, "http://example.com/product1.jpg");
        Product product2 = new Product("Product 2", 200, "http://example.com/product2.jpg");
        Product product3 = new Product("Product 3", 300, "http://example.com/product3.jpg");

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
    }

    @Test
    void testGetPagedProducts() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<Product> productPage = productService.findAll(pageable);

        assertThat(productPage).isNotNull();
        assertThat(productPage.getContent()).hasSize(2);
        assertThat(productPage.getTotalElements()).isEqualTo(3);
        assertThat(productPage.getTotalPages()).isEqualTo(2);
        assertThat(productPage.getContent().get(0).getName()).isEqualTo("Product 1");
        assertThat(productPage.getContent().get(1).getName()).isEqualTo("Product 2");
    }

}