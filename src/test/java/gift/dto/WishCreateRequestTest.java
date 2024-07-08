package gift.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gift.dto.wish.WishCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WishCreateRequestTest {

    @Test
    @DisplayName("WishCreateRequestDTO 생성 테스트")
    public void testCreateWishCreateRequestDTO() {
        Long productId = 1L;
        WishCreateRequest wishCreateRequest = new WishCreateRequest(productId);

        assertThat(wishCreateRequest.productId()).isEqualTo(productId);
    }

    @Test
    @DisplayName("WishCreateRequestDTO 필드 값 테스트")
    public void testWishCreateRequestDTOFields() {
        Long productId = 2L;
        WishCreateRequest wishCreateRequest = new WishCreateRequest(productId);

        assertThat(wishCreateRequest.productId()).isEqualTo(productId);
    }
}
