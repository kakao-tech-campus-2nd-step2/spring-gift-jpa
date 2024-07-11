package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.dto.WishRequest;
import gift.entity.WishEntity;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;
    @Test
    @DisplayName("memberId로 wish 리스트 가져오는 findAllByMember 테스트")
    void findAllByMemberId() {
        // given
        WishRequest request1 = new WishRequest(1L, 1L);
        WishRequest request2 = new WishRequest(1L, 2L);
        WishEntity expected1 = wishRepository.save(request1.toWishEntity());
        WishEntity expected2 = wishRepository.save(request2.toWishEntity());

        // when
        List<WishEntity> actualList = wishRepository.findAllByMemberId(request1.getMemberId());

        // then
        assertThat(actualList).isNotNull();
        assertThat(actualList).hasSize(2);
        assertThat(actualList).containsExactlyInAnyOrder(expected1, expected2);
    }

    @Test
    @DisplayName("findById 테스트")
    void findById(){
        // given
        WishRequest request = new WishRequest(1L, 1L);
        WishEntity expected = wishRepository.save(request.toWishEntity());

        // when
        WishEntity actual = wishRepository.findById(expected.getId()).orElseThrow();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("save 테스트")
    void save(){
        // given
        WishRequest request = new WishRequest(1L, 1L);
        WishEntity expected = request.toWishEntity();

        // when
        WishEntity actual = wishRepository.save(expected);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getMemberId()).isEqualTo(expected.getMemberId()),
            () -> assertThat(actual.getProductId()).isEqualTo(expected.getProductId())
        );
    }

    @Test
    @DisplayName("delete 테스트")
    void delete(){
        // given
        WishRequest request = new WishRequest(1L, 1L);
        WishEntity savedWish = wishRepository.save(request.toWishEntity());

        // when
        wishRepository.delete(savedWish);

        // then
        assertTrue(wishRepository.findById(savedWish.getId()).isEmpty());
    }
}