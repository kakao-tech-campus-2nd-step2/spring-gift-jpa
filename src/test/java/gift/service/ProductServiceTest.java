package gift.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import gift.domain.Product;
import gift.dto.ProductDTO;
import gift.repository.ProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @DisplayName("상품 추가")
    @Test
    void addProduct() {
        // given
        Product product = new Product(1L, "test", 1234, "testImage");
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // when
        ProductDTO actual = productService.addProduct(product.toDTO());

        // then
        assertThat(actual).isEqualTo(product.toDTO());
    }

    @DisplayName("id로 상품 찾기")
    @Test
    void getProduct() {
        // given
        long id = 1L;
        Product product = new Product(id, "test", 1234, "testImage");
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        // when
        ProductDTO actual = productService.getProduct(id);

        // then
        assertThat(actual).isEqualTo(product.toDTO());
    }

    @DisplayName("상품 수정")
    @Test
    void updateProduct() {
        // given
        long id = 1L;
        Product updatedProduct = new Product(id, "updatedTest", 12345, "updatedTestImage");
        when(productRepository.findById(id)).thenReturn(Optional.of(updatedProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // when
        ProductDTO actual = productService.updateProduct(id, updatedProduct.toDTO());

        // then
        assertThat(actual).isEqualTo(updatedProduct.toDTO());
    }
}
