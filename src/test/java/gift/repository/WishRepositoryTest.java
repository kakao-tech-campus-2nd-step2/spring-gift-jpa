package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.model.Member;
import gift.model.Wish;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void saveMember() {
        var member = new Member("test@email.com", "test");
        var savedMember = memberRepository.save(member);
    }


    @Test
    void saveWishTest() {
        var expected = new Wish(memberRepository.findAll().getFirst().getId(), "상품명");

        var actual = wishRepository.save(expected);
        assertAll(
            () -> assertThat(actual).isNotNull(),
            () -> assertThat(actual.getId()).isEqualTo(expected.getId())
        );
    }

    @Test
    void findWishByIdTest() {
        var expected = new Wish(memberRepository.findAll().getFirst().getId(), "상품명");

        var actual = wishRepository.save(expected);

        assertAll(
            () -> {
                assertTrue(wishRepository.findById(expected.getId()).isPresent());
                assertThat(wishRepository.findById(expected.getId()).get()).isEqualTo(expected);
            }
        );
    }

    @Test
    void findWishByMemberIdTest() {
        var expected = new Wish(memberRepository.findAll().getFirst().getId(), "상품명");
        wishRepository.save(expected);

        var actual = wishRepository.findByMemberId(
            memberRepository.findAll().getFirst().getId());

        assertThat(actual.getFirst()).isEqualTo(expected);
    }

    @Test
    void deleteWishByMemberIdAndId() {
        var expected = new Wish(memberRepository.findAll().getFirst().getId(), "상품명");
        var savedWish = wishRepository.save(expected);

        var b = wishRepository.deleteByIdAndMemberId(savedWish.getId(),
            memberRepository.findAll().getFirst().getId());
        System.out.println(wishRepository.findAll());
        assertTrue(wishRepository.findAll().isEmpty());
    }
}