package gift.service;

import gift.dto.request.ProductRequest;
import gift.domain.Product;
import gift.exception.ProductNotFoundException;
import gift.repository.product.ProductSpringDataJpaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductSpringDataJpaRepository productRepository;

    @Test
    public void testRegister() {
        ProductRequest productRequest = new ProductRequest("Product 1", 100, "test-url");
        Product product = Product.RequestToEntity(productRequest);

        given(productRepository.save(Mockito.any(Product.class))).willReturn(product);

        Product savedProduct = productService.register(productRequest);

        assertThat(savedProduct.getName()).isEqualTo("Product 1");
    }

    @Test
    public void testFindOne() {
        Long productId = 1L;
        Product product = new Product("Product 1", 100,"test-url");
        product.setId(productId);

        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));

        Product foundProduct = productService.findOne(product.getId());

        assertThat(foundProduct.getName()).isEqualTo("Product 1");
    }

    @Test
    public void testFindOneWithNonExistentId() {
        Long nonExistentId = 999L;

        given(productRepository.findById(nonExistentId)).willReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.findOne(nonExistentId);
        });
    }
}
