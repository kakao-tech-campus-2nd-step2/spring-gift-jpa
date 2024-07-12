package gift.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gift.dto.wish.WishRequest;
import gift.model.Member;
import gift.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WishRequestTest {

    @Test
    @DisplayName("WishRequestDTO 생성 테스트")
    public void testCreateWishRequestDTO() {
        Member member = new Member(1L, "test@example.com", "password");
        Product product = new Product(1L, "Product1", 100, "imageUrl1");
        WishRequest wishRequest = new WishRequest(member, product);

        assertThat(wishRequest.member()).isEqualTo(member);
        assertThat(wishRequest.product()).isEqualTo(product);
    }

    @Test
    @DisplayName("WishRequestDTO 필드 값 테스트")
    public void testWishRequestDTOFields() {
        Member member = new Member(2L, "test2@example.com", "password");
        Product product = new Product(2L, "Product2", 200, "imageUrl2");
        WishRequest wishRequest = new WishRequest(member, product);

        assertThat(wishRequest.member()).isEqualTo(member);
        assertThat(wishRequest.product()).isEqualTo(product);
    }
}
