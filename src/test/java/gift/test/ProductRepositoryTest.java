package gift.test;

import static org.assertj.core.api.Assertions.assertThat;

import gift.exception.ErrorCode;
import gift.exception.InvalidProductNameException;
import java.util.Optional;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import gift.model.Product;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;


    @Test
    @Order(1)
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
    @Order(2)
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
    @Order(3)
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



    private void validateProductName(String name) {
        if (name.length() > 15) {
            throw new InvalidProductNameException(ErrorCode.INVALID_NAME_LENGTH);
        }
        if (!Pattern.matches("[a-zA-Z0-9가-힣()\\[\\]+\\-&/_ ]*", name)) {
            throw new InvalidProductNameException(ErrorCode.INVALID_CHARACTERS);
        }
        if (name.contains("카카오")) {
            throw new InvalidProductNameException(ErrorCode.CONTAINS_KAKAO);
        }
    }


    @Test
    @Order(4)
    @DisplayName("상품 이름이 15글자가 넘어갈때")
    void testInvalidNameLength() {
        String longName = "veryveryveryveryveryveryLong";
        InvalidProductNameException exception = Assertions.assertThrows(
            InvalidProductNameException.class, () -> {
                validateProductName(longName);
            });
        Assertions.assertEquals("상품 이름은 공백 포함 15글자를 넘을 수 없습니다.", exception.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName("상품 이름에 허용되지 않은 특수기호가 들어갈때")
    void testInvalidCharacters() {
        String invalidName = "Invalid@Name#";
        InvalidProductNameException exception = Assertions.assertThrows(InvalidProductNameException.class, () -> {
            validateProductName(invalidName);
        });
        Assertions.assertEquals("상품 이름에는 특수문자 ( ), [ ], +, -, &, /, _ 만 사용할 수 있습니다.", exception.getMessage());
    }

    @Test
    @Order(6)
    @DisplayName("상품 이름에 카카오 라는 문구가 들어갈때")
    void testContainsKakao() {
        String nameWithKakao = "카카오Product";
        InvalidProductNameException exception = Assertions.assertThrows(InvalidProductNameException.class, () -> {
            validateProductName(nameWithKakao);
        });
        Assertions.assertEquals("상품 이름에 '카카오'가 포함되면 담당 MD와 협의가 필요합니다.", exception.getMessage());
    }

}
