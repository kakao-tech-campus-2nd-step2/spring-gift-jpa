package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import gift.product.dto.ClientProductDto;
import gift.product.model.Product;
import gift.product.service.ProductService;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ProductServiceTest {

    final ProductService productService;

    @Autowired
    ProductServiceTest(ProductService productService) {
        this.productService = productService;
    }

    @AfterEach
    void 상품_초기화() {
        List<Product> products = productService.getProductAll();
        for (Product product : products) {
            productService.deleteProduct(product.getId());
        }
    }

    @Test
    void 상품_추가() {
        ClientProductDto productDTO = new ClientProductDto("사과", 3000, "사진링크");
        Product product = productService.insertProduct(productDTO);

        assertSoftly(softly -> {
            assertThat(product.getName()).isEqualTo("사과");
            assertThat(product.getPrice()).isEqualTo(3000);
            assertThat(product.getImageUrl()).isEqualTo("사진링크");
        });
    }

    @Test
    void 상품_조회() {
        ClientProductDto productDTO = new ClientProductDto("사과", 3000, "사진링크");
        Product insertedProduct = productService.insertProduct(productDTO);

        Product product = productService.getProduct(insertedProduct.getId());

        assertSoftly(softly -> {
            assertThat(product.getName()).isEqualTo("사과");
            assertThat(product.getPrice()).isEqualTo(3000);
            assertThat(product.getImageUrl()).isEqualTo("사진링크");
        });

    }

    @Test
    void 상품_전체_조회() {
        ClientProductDto productDTO = new ClientProductDto("사과", 3000, "사진링크");
        productService.insertProduct(productDTO);

        List<Product> productAll = productService.getProductAll();

        assertSoftly(softly -> {
            assertThat(productAll.get(0).getName()).isEqualTo("사과");
            assertThat(productAll.get(0).getPrice()).isEqualTo(3000);
            assertThat(productAll.get(0).getImageUrl()).isEqualTo("사진링크");
        });
    }

    @Test
    void 상품_전체_조회_페이지() {
        int PRODUCT_COUNT = 9;
        int PAGE = 1;
        int SIZE = 4;
        String SORT = "name";
        String DIRECTION = "desc";

        for (int i = 1; i <= PRODUCT_COUNT; i++) {
            productService.insertProduct(new ClientProductDto("테스트" + i, 1000 + i, "테스트주소" + i));
        }

        Pageable pageable = PageRequest.of(PAGE, SIZE, Sort.Direction.fromString(DIRECTION), SORT);
        Page<Product> products = productService.getProductAll(pageable);

        assertSoftly(softly -> {
            assertThat(products.getTotalPages()).isEqualTo(
                (int) Math.ceil((double) PRODUCT_COUNT / SIZE));
            assertThat(products.getTotalElements()).isEqualTo(PRODUCT_COUNT);
            assertThat(products.getSize()).isEqualTo(SIZE);
            assertThat(products.getContent().get(0).getName()).isEqualTo(
                "테스트" + (PRODUCT_COUNT - SIZE));
        });
    }

    @Test
    void 상품_수정() {
        ClientProductDto productDTO = new ClientProductDto("사과", 3000, "사진링크");
        Product product = productService.insertProduct(productDTO);

        ClientProductDto productUpdatedDTO = new ClientProductDto("사과", 5500, "사진링크2");

        Product productUpdated = productService.updateProduct(product.getId(), productUpdatedDTO);

        assertSoftly(softly -> {
            assertThat(productUpdated.getName()).isEqualTo("사과");
            assertThat(productUpdated.getPrice()).isEqualTo(5500);
            assertThat(productUpdated.getImageUrl()).isEqualTo("사진링크2");
        });
    }

    @Test
    void 상품_삭제() {
        ClientProductDto productDTO = new ClientProductDto("사과", 3000, "사진링크");
        productService.insertProduct(productDTO);

        productDTO = new ClientProductDto("바나나", 1500, "사진링크2");
        Product product = productService.insertProduct(productDTO);

        productService.deleteProduct(product.getId());

        List<Product> productAll = productService.getProductAll();
        assertThat(productAll).hasSize(1);
    }

    @Test
    void 존재하지_않는_상품_조회() {
        assertThatThrownBy(() -> productService.getProduct(-1L)).isInstanceOf(
            NoSuchElementException.class);
    }
}
