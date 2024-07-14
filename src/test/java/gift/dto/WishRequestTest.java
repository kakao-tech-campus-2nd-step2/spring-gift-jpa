package gift.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gift.dto.wish.WishRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WishRequestTest {

    @Test
    @DisplayName("WishRequestDTO 생성 테스트")
    public void testCreateWishRequestDTO() {
        Long memberId = 1L;
        Long productId = 1L;
        WishRequest wishRequest = new WishRequest(memberId, productId);

        assertThat(wishRequest.memberId()).isEqualTo(memberId);
        assertThat(wishRequest.productId()).isEqualTo(productId);
    }

    @Test
    @DisplayName("WishRequestDTO 필드 값 테스트")
    public void testWishRequestDTOFields() {
        Long memberId = 2L;
        Long productId = 2L;
        WishRequest wishRequest = new WishRequest(memberId, productId);

        assertThat(wishRequest.memberId()).isEqualTo(memberId);
        assertThat(wishRequest.productId()).isEqualTo(productId);
    }
}
