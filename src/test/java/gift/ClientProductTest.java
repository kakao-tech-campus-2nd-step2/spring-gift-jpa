package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import gift.product.dto.ClientProductDto;
import gift.product.dto.LoginMember;
import gift.product.model.Product;
import gift.product.service.ProductService;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ClientProductTest {

    final ProductService productService;
    final LoginMember loginMember = new LoginMember(1L);

    @Autowired
    ClientProductTest(ProductService productService) {
        this.productService = productService;
    }

    @AfterEach
    void 상품_초기화() {
        List<Product> products = productService.getProductAll(loginMember);
        for (Product product : products) {
            productService.deleteProduct(product.getId(), loginMember);
        }
    }

    @Test
    void 상품_추가_테스트() {
        ClientProductDto productDTO = new ClientProductDto("사과", 3000, "사진링크");
        Product product = productService.insertProduct(productDTO, loginMember);

        assertSoftly(softly -> {
            assertThat(product.getName()).isEqualTo("사과");
            assertThat(product.getPrice()).isEqualTo(3000);
            assertThat(product.getImageUrl()).isEqualTo("사진링크");
        });
    }

    @Test
    void 상품_조회_테스트() {
        ClientProductDto productDTO = new ClientProductDto("사과", 3000, "사진링크");
        Product insertedProduct = productService.insertProduct(productDTO, loginMember);

        Product product = productService.getProduct(insertedProduct.getId(), loginMember);

        assertSoftly(softly -> {
            assertThat(product.getName()).isEqualTo("사과");
            assertThat(product.getPrice()).isEqualTo(3000);
            assertThat(product.getImageUrl()).isEqualTo("사진링크");
        });

    }

    @Test
    void 상품_전체_조회_테스트() {
        ClientProductDto productDTO = new ClientProductDto("사과", 3000, "사진링크");
        productService.insertProduct(productDTO, loginMember);

        List<Product> productAll = productService.getProductAll(loginMember);

        assertSoftly(softly -> {
            assertThat(productAll.get(0).getName()).isEqualTo("사과");
            assertThat(productAll.get(0).getPrice()).isEqualTo(3000);
            assertThat(productAll.get(0).getImageUrl()).isEqualTo("사진링크");
        });
    }

    @Test
    void 상품_수정_테스트() {
        ClientProductDto productDTO = new ClientProductDto("사과", 3000, "사진링크");
        Product product = productService.insertProduct(productDTO, loginMember);

        ClientProductDto productUpdatedDTO = new ClientProductDto("사과", 5500, "사진링크2");

        Product productUpdated = productService.updateProduct(product.getId(), productUpdatedDTO,
            loginMember);

        assertSoftly(softly -> {
            assertThat(productUpdated.getName()).isEqualTo("사과");
            assertThat(productUpdated.getPrice()).isEqualTo(5500);
            assertThat(productUpdated.getImageUrl()).isEqualTo("사진링크2");
        });
    }

    @Test
    void 상품_삭제_테스트() {
        ClientProductDto productDTO = new ClientProductDto("사과", 3000, "사진링크");
        productService.insertProduct(productDTO, loginMember);

        productDTO = new ClientProductDto("바나나", 1500, "사진링크2");
        Product product = productService.insertProduct(productDTO, loginMember);

        productService.deleteProduct(product.getId(), loginMember);

        List<Product> productAll = productService.getProductAll(loginMember);
        assertThat(productAll).hasSize(1);
    }
}
