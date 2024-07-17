package gift.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import gift.domain.dto.request.ProductRequest;
import gift.domain.dto.response.ProductResponse;
import gift.domain.entity.Product;
import gift.domain.exception.ProductAlreadyExistsException;
import gift.domain.exception.ProductNotFoundException;
import gift.domain.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    public void beforeEach() {
        product = new Product("name", 1000, "image.png");
        ReflectionTestUtils.setField(product, "id", 1L);
    }

    @Test
    @DisplayName("상품 얻기 - 성공")
    void getProductById() {
        //given
        given(productRepository.findById(any(Long.class))).willReturn(Optional.of(product));

        //when
        Product result = productService.getProductById(product.getId());

        //then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(product);
    }

    @Test
    @DisplayName("상품 얻기 - 실패")
    void getProductById_Fail() {
        //given
        given(productRepository.findById(any(Long.class))).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> productService.getProductById(product.getId()))
            .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("모든 상품 얻기")
    void getAllProducts() {
        //given
        List<Product> productList = List.of(
            new Product("name1", 1000, "image1.png"),
            new Product("name2", 2000, "image2.png"),
            new Product("name3", 3000, "image3.png"));
        ReflectionTestUtils.setField(productList.get(0), "id", 1L);
        ReflectionTestUtils.setField(productList.get(1), "id", 2L);
        ReflectionTestUtils.setField(productList.get(2), "id", 3L);
        List<ProductResponse> expected = List.of(
            new ProductResponse(1L, "name1", 1000, "image1.png"),
            new ProductResponse(2L, "name2", 2000, "image2.png"),
            new ProductResponse(3L, "name3", 3000, "image3.png"));
        given(productRepository.findAll()).willReturn(productList);

        //when
        List<ProductResponse> actual = productService.getAllProducts();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("상품 추가 - 성공")
    void addProduct() {
        //given
        ProductRequest request = new ProductRequest("name", 1000,"image.png");
        ProductResponse expected = new ProductResponse(1L, "name", 1000, "image.png");
        given(productRepository.findByContents(any(ProductRequest.class)))
            .willReturn(Optional.empty());
        given(productRepository.save(any(Product.class))).willReturn(product);

        //when
        ProductResponse actual = productService.addProduct(request);

        //then
        assertThat(actual).isEqualTo(expected);
        then(productRepository).should(times(1)).findByContents(eq(request));
        then(productRepository).should(times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("상품 추가 - 이미 있는 상품 등록시")
    void addProduct_AlreadyExists() {
        //given
        ProductRequest request = new ProductRequest("name", 1000,"image.png");
        given(productRepository.findByContents(any(ProductRequest.class)))
            .willReturn(Optional.of(product));

        //when & then
        assertThatThrownBy(() -> productService.addProduct(request)).isInstanceOf(ProductAlreadyExistsException.class);
        then(productRepository).should(times(1)).findByContents(eq(request));
        then(productRepository).should(never()).save(any(Product.class));
    }

    @Test
    @DisplayName("상품 업데이트")
    void updateProductById() {
        //given
        Long id = 1L;
        ProductRequest request = new ProductRequest("newName", 10000, "newImage.jpg");
        given(productRepository.findById(any(Long.class))).willReturn(Optional.of(product));
        ProductResponse expected = new ProductResponse(1L, "newName", 10000, "newImage.jpg");

        //when
        ProductResponse actual = productService.updateProductById(id, request);

        //then
        assertThat(actual).isEqualTo(expected);
        then(productRepository).should(times(1)).findById(eq(id));
    }
}