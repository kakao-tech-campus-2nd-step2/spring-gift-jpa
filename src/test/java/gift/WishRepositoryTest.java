package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import gift.product.model.Member;
import gift.product.model.Wish;
import gift.product.repository.AuthRepository;
import gift.product.repository.WishRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
class WishRepositoryTest {
    @Autowired
    WishRepository wishRepository;

    @Autowired
    AuthRepository authRepository;

    @Test
    void 위시리스트_항목_추가() {
        Member member = new Member("test@test.com", "1234");
        member = authRepository.save(member);
        Wish wish = new Wish(member, 1L);
        Wish insertedWish = wishRepository.save(wish);

        assertSoftly(softly -> {
            assertThat(insertedWish.getId()).isNotNull();
            assertThat(insertedWish.getMember().getId()).isNotNull();
            assertThat(insertedWish.getProductId()).isEqualTo(1L);
        });
    }

    @Test
    void 위시리스트_전체_조회() {
        Member member = new Member("test@test.com", "1234");
        member = authRepository.save(member);
        wishRepository.save(new Wish(member, 1L));
        wishRepository.save(new Wish(member, 2L));

        List<Wish> wishes = wishRepository.findAllByMemberId(member.getId());

        assertThat(wishes).hasSize(2);
    }

    @Test
    void 위시리스트_조회() {
        Member member = new Member("test@test.com", "1234");
        member = authRepository.save(member);
        wishRepository.save(new Wish(member, 1L));

        boolean isPresentWish = wishRepository.findByIdAndMemberId(1L, member.getId()).isPresent();

        assertThat(isPresentWish).isTrue();
    }

    @Test
    void 위시리스트_항목_삭제() {
        Member member = new Member("test@test.com", "1234");
        member = authRepository.save(member);
        Wish wish = wishRepository.save(new Wish(member, 1L));
        wishRepository.deleteById(wish.getId());
        boolean isPresentWish = wishRepository.findByIdAndMemberId(wish.getId(), member.getId()).isPresent();

        assertThat(isPresentWish).isFalse();
    }
}
