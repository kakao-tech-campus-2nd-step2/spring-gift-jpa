package gift.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void testCreateValidProduct() {
        Product product = new Product(1L, "상품", "100", "https://kakao");
        assertAll(
            () -> assertThat(product.getId()).isNotNull(),
            () -> assertThat(product.getName()).isEqualTo("상품"),
            () -> assertThat(product.getPrice()).isEqualTo("100"),
            () -> assertThat(product.getImageUrl()).isEqualTo("https://kakao")
        );
    }

    @Test
    void testCreateWithNullName() {
        try {
            Product nullNameProduct = new Product(1L, null, "100", "https://kakao");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithEmptyName() {
        try {
            Product emptyNameProduct = new Product(1L, "", "200", "https://kakao");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithLengthName() {
        try {
            Product lengthNameProduct = new Product(1L, "aaaa aaaa aaaa a", "200", "https://kakao");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithInvalidName() {
        try {
            Product invalidNameProduct = new Product(1L, ".", "100", "https://kakao");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithKaKaoName() {
        try {
            Product kakaoNameProduct = new Product(1L, "카카오", "100", "https://kakao");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }


    @Test
    void testCreateWithNullPrice() {
        try {
            Product nullPriceProduct = new Product(1L, "상품", null, "https://kakao");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithEmptyPrice() {
        try {
            Product emptyPriceProduct = new Product(1L, "상품", "", "https://kakao");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }


    @Test
    void testCreateWithInvalidPrice() {
        try {
            Product invalidPriceProduct = new Product(1L, "상품", "abcde", "https://kakao");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithNullImageUrl() {
        try {
            Product nullImageUrlProduct = new Product(1L, "상품", "100", null);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithEmptyImageUrl() {
        try {
            Product emptyImageUrlProduct = new Product(1L, "상품", "100", "");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithInvalidImageUrl() {
        try {
            Product invalidImageUrlProduct = new Product(1L, "상품", "100", "kbm");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }
}