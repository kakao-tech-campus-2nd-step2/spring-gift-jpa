package gift.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.domain.Wish;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Test
    @DisplayName("특정 회원의 Wishlist 조회 테스트")
    void testFindWishListById() {
        // given
        Long memberId = 1L;
        Wish wish1 = new Wish();
        wish1.setMemberId(memberId);
        wish1.setProductId(100L);

        Wish wish2 = new Wish();
        wish2.setMemberId(memberId);
        wish2.setProductId(200L);

        wishRepository.save(wish1);
        wishRepository.save(wish2);

        // when
        List<Wish> wishList = wishRepository.findAllByMemberId(memberId);

        // then
        assertThat(wishList).hasSize(2);
        assertThat(wishList).extracting(Wish::getProductId).containsExactlyInAnyOrder(100L, 200L);
    }

    @Test
    @DisplayName("Wish 추가 테스트")
    void testAddWish() {
        // given
        Long memberId = 2L;
        Long productId = 300L;

        Wish wish = new Wish();
        wish.setMemberId(memberId);
        wish.setProductId(productId);

        // when
        wishRepository.save(wish);

        // then
        List<Wish> wishList = wishRepository.findAllByMemberId(memberId);
        assertThat(wishList).hasSize(1);
        assertThat(wishList.get(0).getProductId()).isEqualTo(productId);
    }

    @Test
    @DisplayName("Wish 삭제 테스트")
    void testDeleteWish() {
        // given
        Long memberId = 3L;
        Long productId = 400L;

        Wish wish = new Wish();
        wish.setMemberId(memberId);
        wish.setProductId(productId);
        wishRepository.save(wish);

        // when
        wishRepository.deleteByMemberIdAndProductId(memberId, productId);

        // then
        List<Wish> wishList = wishRepository.findAllByMemberId(memberId);
        assertThat(wishList).isEmpty();
    }

}