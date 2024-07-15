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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
        List<Product> products = List.of(
            new Product(1L, "책", 20000, "https://example.com/book.jpg"),
            new Product(2L, "스마트폰", 800000, "https://example.com/smartphone.jpg"),
            new Product(3L, "키보드", 50000, "https://example.com/keyboard.jpg"),
            new Product(4L, "마우스", 30000, "https://example.com/mouse.jpg"),
            new Product(5L, "이어폰", 60000, "https://example.com/earphone.jpg"),
            new Product(6L, "헤드폰", 100000, "https://example.com/headphone.jpg"),
            new Product(7L, "카메라", 300000, "https://example.com/camera.jpg"),
            new Product(8L, "그래픽 카드", 80000, "https://example.com/gpu.jpg"),
            new Product(9L, "메모리", 150000, "https://example.com/memory.jpg"),
            new Product(10L, "모니터", 400000, "https://example.com/monitor.jpg"),
            new Product(11L, "마우스 패드", 2000, "https://example.com/mousepad.jpg"),
            new Product(12L, "키링", 10000, "https://example.com/keyring.jpg"),
            new Product(13L, "백팩", 70000, "https://example.com/backpack.jpg"),
            new Product(14L, "의자", 150000, "https://example.com/chair.jpg"),
            new Product(15L, "커피머신", 25000, "https://example.com/coffee-machine.jpg"),
            new Product(16L, "라면", 3000, "https://example.com/ramen.jpg"),
            new Product(17L, "스피커", 80000, "https://example.com/speaker.jpg"),
            new Product(18L, "텔레비전", 500000, "https://example.com/television.jpg"),
            new Product(19L, "게임기", 350000, "https://example.com/game-console.jpg"),
            new Product(20L, "블루투스 스피커", 9000, "https://example.com/bluetooth-speaker.jpg"),
            new Product(21L, "선풍기", 40000, "https://example.com/fan.jpg"),
            new Product(22L, "에어컨", 700000, "https://example.com/air-conditioner.jpg"),
            new Product(23L, "냉장고", 900000, "https://example.com/fridge.jpg"),
            new Product(24L, "전기밥솥", 150000, "https://example.com/rice-cooker.jpg"),
            new Product(25L, "전자레인지", 100000, "https://example.com/microwave.jpg"),
            new Product(26L, "믹서기", 80000, "https://example.com/blender.jpg"),
            new Product(27L, "청소기", 200000, "https://example.com/vacuum.jpg"),
            new Product(28L, "다리미", 30000, "https://example.com/iron.jpg"),
            new Product(29L, "세탁기", 600000, "https://example.com/washer.jpg"),
            new Product(30L, "건조기", 500000, "https://example.com/dryer.jpg"),
            new Product(31L, "전기포트", 25000, "https://example.com/kettle.jpg"),
            new Product(32L, "토스터기", 30000, "https://example.com/toaster.jpg"),
            new Product(33L, "헤어드라이기", 40000, "https://example.com/hair-dryer.jpg"),
            new Product(34L, "헤어스타일러", 70000, "https://example.com/hair-styler.jpg"),
            new Product(35L, "수세미", 2000, "https://example.com/scrubber.jpg"),
            new Product(36L, "비타민", 30000, "https://example.com/vitamin.jpg"),
            new Product(37L, "운동화", 120000, "https://example.com/sneakers.jpg")
        );
        List<String> expectedNames = products.stream().map(Product::getName).toList();
        given(productJpaRepository.findAll(any(Pageable.class))).willReturn(
            new PageImpl<>(products, PageRequest.of(0, 5), products.size()));

        // when
        Page<Product> actual = productService.readAll(PageRequest.of(0, 5));
        List<String> actualNames = actual.getContent().stream().map(Product::getName).toList();

        // then
        assertAll(
            () -> assertThat(actual.getSize()).isEqualTo(5),
            () -> assertThat(actualNames). isEqualTo(expectedNames),
            () -> assertThat(actual.getTotalElements()).isEqualTo(37),
            () -> assertThat(actual.getTotalPages()).isEqualTo(8)
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
        Page<Product> productList = productService.readAll(PageRequest.of(0, 10));
        assertThat(productList).isNull();
    }
}