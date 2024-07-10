package gift.domain.product.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import gift.domain.product.dao.ProductJpaRepository;
import gift.domain.product.dto.ProductDto;
import gift.domain.product.entity.Product;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@AutoConfigureMockMvc
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductJpaRepository productJpaRepository;

    private static final ProductDto productDto = new ProductDto(null, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");

    @Test
    @DisplayName("상품 생성 서비스 테스트")
    void create() {
        // given
        Product expected = productDto.toProduct();
        expected.setId(1L);
        given(productJpaRepository.save(any(Product.class))).willReturn(expected);

        // when
        Product actual = productService.create(productDto);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    @DisplayName("상품 전체 조회 서비스 테스트")
    void readAll() {
        // given
        Product product = productDto.toProduct();
        product.setId(1L);
        List<Product> expected = List.of(product);
        given(productJpaRepository.findAll()).willReturn(expected);

        // when
        List<Product> actual = productService.readAll();

        // then
        assertAll(
            () -> assertThat(actual.size()).isEqualTo(1),
            () -> assertThat(actual.get(0).getId()).isEqualTo(expected.get(0).getId()),
            () -> assertThat(actual.get(0).getName()).isEqualTo(expected.get(0).getName()),
            () -> assertThat(actual.get(0).getPrice()).isEqualTo(expected.get(0).getPrice()),
            () -> assertThat(actual.get(0).getImageUrl()).isEqualTo(expected.get(0).getImageUrl())
        );
    }

    @Test
    @DisplayName("상품 조회 서비스 테스트")
    void readById() {
        // given
        Product expected = productDto.toProduct();
        expected.setId(1L);
        given(productJpaRepository.findById(anyLong())).willReturn(Optional.of(expected));

        // when
        Product actual = productService.readById(1L);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    @DisplayName("상품 수정 서비스 테스트")
    void update() {
        // given
        Product product = productDto.toProduct();
        product.setId(1L);
        ProductDto productDto = new ProductDto(null, "아이스 카페 라떼 T", 4500, "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg");

        given(productJpaRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(productJpaRepository.save(any(Product.class))).willReturn(product);

        // when
        Product actual = productService.update(1L, productDto);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(productDto.name()),
            () -> assertThat(actual.getPrice()).isEqualTo(productDto.price()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(productDto.imageUrl())
        );
    }

    @Test
    @DisplayName("상품 삭제 서비스 테스트")
    void delete() {
        // given
        Product product = productDto.toProduct();
        product.setId(1L);
        given(productJpaRepository.findById(anyLong())).willReturn(Optional.of(product));
        willDoNothing().given(productJpaRepository).delete(any(Product.class));

        // when
        productService.delete(1L);

        // then
        List<Product> productList = productService.readAll();
        assertThat(productList).isEmpty();
    }
}