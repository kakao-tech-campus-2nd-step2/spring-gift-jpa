package gift.model.product;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    @DisplayName("Product 생성 테스트")
    void product_생성() {
        //given
        String name = "테스트 상품";
        Integer price = 10000;
        String imageUrl = "test.jpg";

        //when
        Product product = Product.create(null, name, price, imageUrl);

        // then
        assertAll(
            () -> assertThat(product.getName()).isEqualTo(name),
            () -> assertThat(product.getPrice()).isEqualTo(price),
            () -> assertThat(product.getImageUrl()).isEqualTo(imageUrl)
        );
    }

    @Test
    @DisplayName("Product 실패 이름 유효성 테스트")
    void product_실패_이름_유효성() {
        //given
        String testName1 = "카카오를 포함하면 안됨";
        String testName2 = "15를_넘으면_제작에_실패해야_합니다";
        String testName3 = "특수문자()[]+-&/_만가능!";

        Integer price = 10000;
        String imageUrl = "test.jpg";

        //when
        // then
        assertThrows(IllegalArgumentException.class, () -> {
            Product.create(null, testName1, price, imageUrl);
        });
        // then
        assertThrows(IllegalArgumentException.class, () -> {
            Product.create(null, testName2, price, imageUrl);
        });
        // then
        assertThrows(IllegalArgumentException.class, () -> {
            Product.create(null, testName3, price, imageUrl);
        });
    }

    @Test
    @DisplayName("Product 성공 이름 유효성 테스트")
    void product_성공_이름_유효성() {
        //given
        String name = "특수문자()[]+-&/_가능";

        Integer price = 10000;
        String imageUrl = "test.jpg";

        //when
        Product product = Product.create(null, name, price, imageUrl);

        // then
        assertAll(
            () -> assertThat(product.getName()).isEqualTo(name),
            () -> assertThat(product.getPrice()).isEqualTo(price),
            () -> assertThat(product.getImageUrl()).isEqualTo(imageUrl)
        );
    }

}
