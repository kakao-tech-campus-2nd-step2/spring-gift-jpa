package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        Product expected = entityToDomain(productEntity);

        doReturn(Optional.of(productEntity)).when(productRepository).findById(id);

        // when
        Product actual = productService.getProduct(id);

        // then
        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    @DisplayName("모든 Product 조회 테스트")
    void findAll() {
        // given
        ProductEntity product1 = new ProductEntity("test1", 1000, "test1.jpg");
        ProductEntity product2 = new ProductEntity("test2", 2000, "test2.jpg");

        List<ProductEntity> productList = Arrays.asList(product1, product2);
        List<Product> expected = productList.stream().map(this::entityToDomain).toList();

        doReturn(productList).when(productRepository).findAll();

        // when
        List<Product> actual = productService.getAllProducts();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(2);
        assertThat(actual).containsExactlyInAnyOrder(expected.get(0), expected.get(1));

    }

    @Test
    @DisplayName("product 저장 테스트")
    void create() {
        // given
        ProductRequest productRequest = new ProductRequest("test", 1000, "test.jpg");
        ProductEntity savedProduct = new ProductEntity(productRequest.getName(), productRequest.getPrice(),
            productRequest.getImageUrl());
        
        Product expected = entityToDomain(savedProduct);

        doReturn(savedProduct).when(productRepository).save(any(ProductEntity.class));

        // when
        Product actual = productService.addProduct(productRequest);

        // then
        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    @DisplayName("product 업데이트 테스트")
    void update() {
        // given
        Long id = 1L;
        ProductRequest productRequest = new ProductRequest("update", 1000, "update.jpg");
        ProductEntity savedProductEntity = new ProductEntity("saved", 2000, "preTest.jpg");
        ProductEntity updatedProductEntity = new ProductEntity("update", 1000, "update.jpg");

        Product expected = entityToDomain(updatedProductEntity);

        doReturn(Optional.of(savedProductEntity)).when(productRepository).findById(id);
        doNothing().when(savedProductEntity).update(productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl());
        doReturn(updatedProductEntity).when(productRepository).save(any(ProductEntity.class));

        // when
        Product actual = productService.updateProduct(id, productRequest);

        // then
        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
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

    private Product entityToDomain(ProductEntity productEntity){
        return new Product(productEntity.getId(), productEntity.getName(), productEntity.getPrice(),productEntity.getImageUrl());
    }
}