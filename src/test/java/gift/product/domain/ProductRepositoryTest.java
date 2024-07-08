package gift.product.domain;

import gift.product.application.command.ProductCreateCommand;
import gift.product.application.command.ProductUpdateCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("DELETE FROM product");
    }

    @Test
    public void 모든_상품_조회_테스트() {
        // Given
        ProductCreateCommand product1 = new ProductCreateCommand("Product1", 1000, "http://example.com/image1.jpg");
        ProductCreateCommand product2 = new ProductCreateCommand("Product2", 2000, "http://example.com/image2.jpg");
        productRepository.addProduct(product1.toProduct());
        productRepository.addProduct(product2.toProduct());

        // When
        List<Product> products = productRepository.findAll();

        // Then
        assertThat(products).hasSize(2);
        assertThat(products.get(0).getName()).isEqualTo("Product1");
        assertThat(products.get(1).getName()).isEqualTo("Product2");
    }

    @Test
    public void 상품_ID로_조회_테스트() {
        // Given
        ProductCreateCommand product1 = new ProductCreateCommand("Product1", 1000, "http://example.com/image1.jpg");
        productRepository.addProduct(product1.toProduct());
        Long productId = productRepository.findAll().get(0).getId();

        // When
        Optional<Product> foundProduct = productRepository.findById(productId);

        // Then
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("Product1");
    }

    @Test
    public void 상품_ID로_조회_실패_테스트() {
        // Given
        Long productId = 1L;

        // When
        Optional<Product> foundProduct = productRepository.findById(productId);

        // Then
        assertThat(foundProduct).isNotPresent();
    }

    @Test
    public void 상품_추가_테스트() {
        // Given
        ProductCreateCommand product1 = new ProductCreateCommand("Product1", 1000, "http://example.com/image1.jpg");

        // When
        productRepository.addProduct(product1.toProduct());

        // Then
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getName()).isEqualTo("Product1");
    }

    @Test
    public void 상품_삭제_테스트() {
        // Given
        ProductCreateCommand product1 = new ProductCreateCommand("Product1", 1000, "http://example.com/image1.jpg");
        productRepository.addProduct(product1.toProduct());
        Long productId = productRepository.findAll().get(0).getId();

        // When
        productRepository.deleteProduct(productId);

        // Then
        List<Product> products = productRepository.findAll();
        assertThat(products).isEmpty();
    }

    @Test
    public void 상품_수정_테스트() {
        // Given
        ProductCreateCommand product1 = new ProductCreateCommand("Product1", 1000, "http://example.com/image1.jpg");
        productRepository.addProduct(product1.toProduct());
        Long productId = productRepository.findAll().get(0).getId();
        ProductUpdateCommand updateCommand = new ProductUpdateCommand(productId, "UpdatedProduct", 1500, "http://example.com/image1_updated.jpg");

        // When
        productRepository.updateProduct(updateCommand.toProduct());

        // Then
        Optional<Product> updatedProduct = productRepository.findById(productId);
        assertThat(updatedProduct).isPresent();
        assertThat(updatedProduct.get().getName()).isEqualTo("UpdatedProduct");
        assertThat(updatedProduct.get().getPrice()).isEqualTo(1500);
        assertThat(updatedProduct.get().getImageUrl()).isEqualTo("http://example.com/image1_updated.jpg");
    }
}
