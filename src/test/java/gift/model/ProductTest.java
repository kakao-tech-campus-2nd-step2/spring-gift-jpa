package gift.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import gift.common.exception.ProductNoConferredException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {
    @Test
    @DisplayName("Product 객체 생성 테스트[성공]")
    void createProduct() {
        // given
        Long id = 1L;
        String name = "테스트 상품";
        Integer price = 10000;
        String imgUrl = "http://test.com\"";

        // when
        Product product = new Product(id, name, price, imgUrl);

        // then
        assertThat(product.getId()).isEqualTo(id);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getImgUrl()).isEqualTo(imgUrl);
        assertThat(product.isDeleted()).isFalse();
    }

    @Test
    @DisplayName("Product 객체 생성 테스트[카카오가 포함된 이름]")
    void createProductWithKakao() {
        // given
        Long id = 1L;
        String name = "카카오 상품";
        Integer price = 10000;
        String imgUrl = "http://test.com";

        // when & then
        assertThatThrownBy(() -> new Product(id, name, price, imgUrl))
                .isInstanceOf(ProductNoConferredException.class);
    }
}