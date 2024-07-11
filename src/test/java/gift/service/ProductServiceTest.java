package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.spy;

import gift.domain.Product;
import gift.dto.ProductRequest;
import gift.entity.ProductEntity;
import gift.repository.ProductRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    void find(){
        // given
        Long id = 1L;
        ProductEntity productEntity = new ProductEntity("test", 1000, "test.jpg");
        ProductEntity spyProductEntity = spy(productEntity);

        Product expected = spyProductEntity.toProduct();

        doReturn(Optional.of(spyProductEntity)).when(productRepository).findById(id);
        doReturn(expected).when(spyProductEntity).toProduct();

        // when
        Product actual = productService.find(id);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("모든 Product 조회 테스트")
    void findAll() {
        // given
        ProductEntity product1 = new ProductEntity("test1", 1000, "test1.jpg");
        ProductEntity product2 = new ProductEntity("test2", 2000, "test2.jpg");

        ProductEntity spyProduct1 = spy(product1);
        ProductEntity spyProduct2 = spy(product2);

        List<ProductEntity> productList = Arrays.asList(spyProduct1, spyProduct2);
        List<Product> expected = productList.stream().map(ProductEntity::toProduct).toList();

        doReturn(productList).when(productRepository).findAll();
        doReturn(expected.get(0)).when(spyProduct1).toProduct();
        doReturn(expected.get(1)).when(spyProduct2).toProduct();

        // when
        List<Product> actual = productService.findAll();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("product 저장 테스트")
    void create() {
        // given
        ProductRequest productRequest = new ProductRequest("test", 1000, "test.jpg");
        ProductEntity savedProduct = new ProductEntity(productRequest.getName(), productRequest.getPrice(),
            productRequest.getImageUrl());
        ProductEntity spySavedProduct = spy(savedProduct);
        Product expected = spySavedProduct.toProduct();

        doReturn(spySavedProduct).when(productRepository).save(any(ProductEntity.class));
        doReturn(expected).when(spySavedProduct).toProduct();

        // when
        Product actual = productService.createProduct(productRequest);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("product 업데이트 테스트")
    void update() {
        // given
        Long id = 1L;
        ProductRequest productRequest = new ProductRequest("test", 1000, "test.jpg");
        ProductEntity savedProductEntity = new ProductEntity("preTest", 2000, "preTest.jpg");
        ProductEntity spySavedProductEntity = spy(savedProductEntity);
        Product expect = spySavedProductEntity.toProduct();

        doReturn(Optional.of(spySavedProductEntity)).when(productRepository).findById(id);
        doNothing().when(spySavedProductEntity).updateProductEntity(any(ProductRequest.class));
        doReturn(spySavedProductEntity).when(productRepository).save(any(ProductEntity.class));
        doReturn(expect).when(spySavedProductEntity).toProduct();
        // when
        Product actual = productService.updateProduct(id, productRequest);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    @Test
    @DisplayName("product 삭제 테스트")
    void delete() {
        // given
        Long id = 1L;
        ProductEntity savedProduct = new ProductEntity("test", 1000, "test.jpg");

        doReturn(Optional.of(savedProduct)).when(productRepository).findById(id);

        // when
        productService.deleteProduct(id);

        // then
        verify(productRepository, times(1)).delete(savedProduct);
    }
}