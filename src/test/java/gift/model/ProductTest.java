package gift.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    @DisplayName("Product 모델 생성 테스트")
    public void testCreateProduct() {
        Product product = new Product(1L, "Product1", 100, "imageUrl1");

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("Product1");
        assertThat(product.getPrice()).isEqualTo(100);
        assertThat(product.getImageUrl()).isEqualTo("imageUrl1");
    }

    @Test
    @DisplayName("Product 모델 업데이트 테스트")
    public void testUpdateProduct() {
        Product product = new Product(1L, "Product1", 100, "imageUrl1");
        product.update("Product2", 200, "imageUrl2");

        assertThat(product.getName()).isEqualTo("Product2");
        assertThat(product.getPrice()).isEqualTo(200);
        assertThat(product.getImageUrl()).isEqualTo("imageUrl2");
    }
}
