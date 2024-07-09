package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import gift.domain.Product;
import gift.dto.ProductRequest;
import gift.repository.ProductRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    ProductService productService;
    @Mock
    ProductRepository productRepository;

    @Test
    @DisplayName("Id로 Product 조회 테스트")
    void findTest(){
        // given
        Long id = 1L;
        Product product = new Product(1L, "test", 1000, "test.jpg");
        doReturn(product).when(productRepository).findById(id);

        // when
        Product actualProduct = productService.find(id);

        // then
        assertThat(actualProduct).isEqualTo(product);
    }

    @Test
    @DisplayName("모든 Product 조회 테스트")
    void findAllTest() {
        // given
        Product product1 = new Product(1L, "test1", 1000, "test1.jpg");
        Product product2 = new Product(2L, "test2", 2000, "test2.jpg");
        List<Product> productList = Arrays.asList(product1, product2);

        doReturn(productList).when(productRepository).findAll();

        // when
        List<Product> actualProduct = productService.findAll();

        // then
        assertThat(actualProduct).isEqualTo(productList);
    }

    @Test
    @DisplayName("product 저장 테스트")
    void createTest() {
        // given
        ProductRequest productRequest = new ProductRequest("test", 1000, "test.jpg");
        Product savedProduct = new Product(1L, productRequest.getName(), productRequest.getPrice(),
            productRequest.getImageUrl());

        doReturn(savedProduct).when(productRepository).save(productRequest);

        // when
        Product actualProduct = productService.createProduct(productRequest);

        // then
        assertThat(actualProduct).isEqualTo(savedProduct);
    }

    @Test
    @DisplayName("product 업데이트 테스트")
    void updateTest() {
        // given
        Long id = 1L;
        ProductRequest productRequest = new ProductRequest("test", 1000, "test.jpg");
        Product preProduct = new Product(id, "preTest", 2000, "preTest.jpg");
        Product updatedProduct = new Product(id, productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl());

        doReturn(preProduct).when(productRepository).findById(id);
        doReturn(updatedProduct).when(productRepository).update(any(Product.class));

        // when
        Product actualProduct = productService.updateProduct(id, productRequest);

        // then
        assertThat(actualProduct).isEqualTo(updatedProduct);
    }

    @Test
    @DisplayName("product 삭제 테스트")
    void deleteTest() {
        // given
        Long id = 1L;

        // when
        productService.deleteProduct(id);

        // then
        verify(productRepository, times(1)).delete(id);

    }
}