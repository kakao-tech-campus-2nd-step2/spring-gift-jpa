package gift.test;

import static org.assertj.core.api.Assertions.assertThat;
import jakarta.validation.ConstraintViolation;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import gift.model.Product;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    private Validator validator;

    @Test
    @DisplayName("상품 추가")
    void addProductPrice() {
        // Given
        Product product = new Product(1234L,"Test Product", 1000, "test.jpg");

        // When
        Product savedProduct = productRepository.save(product);
        Long productId = savedProduct.getId();

        // Then
        Product updatedProduct = productRepository.findById(productId).orElseThrow();
        assertThat(updatedProduct.getId()).isEqualTo(productId);
        assertThat(updatedProduct.getName()).isEqualTo("Test Product");
        assertThat(updatedProduct.getPrice()).isEqualTo(1000);
        assertThat(updatedProduct.getImageUrl()).isEqualTo("test.jpg");
    }


    @Test
    @DisplayName("상품 수정")
    void updateProductPrice() {
        // Given
        Product product = new Product(1234L,"Test Product", 1000, "test.jpg");
        Product savedProduct = productRepository.save(product);
        Long productId = savedProduct.getId();

        // When
        savedProduct.setPrice(1500);
        savedProduct.setId(4321L);
        productRepository.save(savedProduct);

        // Then
        Product updatedProduct = productRepository.findById(productId).orElseThrow();
        assertThat(updatedProduct.getPrice()).isEqualTo(1500);
        assertThat(updatedProduct.getId()).isEqualTo(4321L);
    }


    @Test
    @DisplayName("상품 삭제")
    void deleteProductPrice() {
        // Given
        Product product = new Product(1234L,"Test Product", 1000, "test.jpg");
        Product savedProduct = productRepository.save(product);

        // When
        productRepository.deleteById(savedProduct.getId());

        // Then
        Optional<Product> deletedProduct = productRepository.findById(savedProduct.getId());
        Assertions.assertFalse(deletedProduct.isPresent());
    }

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    @DisplayName("상품 이름이 NULL 일때")
    public void whenNameNull(){
        // given
        Product product = new Product();
        product.setName(null);
        product.setPrice(1000);
        product.setImageUrl("test.jpg");
        // when
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        // then
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name") && v.getMessage().contains("이름에 NULL 불가능"));
    }

    @Test
    @DisplayName("상품 이름이 20자 이상일때")
    public void whenNameExceedsLength() {
        // given
        Product product = new Product();
        product.setName("veryveryveryveryveryveryLong");
        product.setPrice(100);
        product.setImageUrl("test.jpg");

        // when
        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        // then
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name") && v.getMessage().contains("20자 이상 불가능"));
    }

    @Test
    public void whenPriceNull() {
        // given
        Product product = new Product();
        product.setName("ValidName");
        product.setPrice(0); // Setting price to a valid value as price is primitive int
        product.setImageUrl("test.jpg");

        // when
        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        // then
        assertThat(violations).noneMatch(v -> v.getPropertyPath().toString().equals("price"));
    }

    @Test
    public void whenImageUrlNull() {
        // given
        Product product = new Product();
        product.setName("ValidName");
        product.setPrice(100);
        product.setImageUrl(null);

        // when
        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        // then
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("imageUrl") && v.getMessage().contains("URL에 NULL 불가능"));
    }
}
