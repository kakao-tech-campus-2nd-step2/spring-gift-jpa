package gift.product;

import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import gift.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ProductCRUDTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private Product exampleProduct;

    @BeforeEach
    public void setUp() {
        exampleProduct = new Product("Ice Americano", 2000, "http://example.com/example.jpg");
        productRepository.save(exampleProduct);
    }

    @Test
    public void testCreateProduct() {
        // Given
        Product product = new Product("CaffeLatte", 2500, "http://example.com/image2.jpg");

        // When
        productService.createProduct(product);

        // Then
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(2);
        assertThat(products).extracting(Product::getName).contains("CaffeLatte");
    }

    @Test
    public void testUpdateProduct() {
        // Given
        Product newProduct = new Product("UpdatedProduct", 3000, "http://example.com/updated.jpg");

        // When
        productService.updateProduct(exampleProduct.getId(), newProduct);

        // Then
        Optional<Product> updatedProduct = productRepository.findById(exampleProduct.getId());
        assertThat(updatedProduct).isPresent();
        assertThat(updatedProduct.get().getName()).isEqualTo("UpdatedProduct");
        assertThat(updatedProduct.get().getPrice()).isEqualTo(3000);
    }

    @Test
    public void testDeleteProduct() {
        // Given
        Long productId = exampleProduct.getId();

        // When
        productService.deleteProduct(productId);

        // Then
        List<Product> products = productRepository.findAll();
        assertThat(products).isEmpty();
    }

    @Test
    public void testGetAllProducts() {
        // Given (initial setup already done in @BeforeEach)

        // When
        List<Product> products = productService.getAllProducts();

        // Then
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getName()).isEqualTo("Ice Americano");
    }

    @Test
    public void testGetProductById() {
        // Given
        Long productId = exampleProduct.getId();

        // When
        Product product = productService.getProductById(productId);

        // Then
        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo("Ice Americano");
    }
}
