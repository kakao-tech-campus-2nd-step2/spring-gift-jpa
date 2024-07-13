package gift.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WishTest {

    @Test
    @DisplayName("Wish 모델 생성 테스트")
    public void testCreateWish() {
        Member member = new Member(1L, "test@example.com", "password");
        Product product = new Product(1L, "Product1", 100, "imageUrl1");
        Wish wish = new Wish(1L, member, product);

        assertThat(wish.getId()).isEqualTo(1L);
        assertThat(wish.getMember()).isEqualTo(member);
        assertThat(wish.getProduct()).isEqualTo(product);
    }

    @Test
    @DisplayName("Wish 소유자 확인 테스트")
    public void testIsOwnedBy() {
        Member member = new Member(1L, "test@example.com", "password");
        Product product = new Product(1L, "Product1", 100, "imageUrl1");
        Wish wish = new Wish(1L, member, product);

        assertThat(wish.isOwnedBy(1L)).isTrue();
        assertThat(wish.isOwnedBy(2L)).isFalse();
    }
}
