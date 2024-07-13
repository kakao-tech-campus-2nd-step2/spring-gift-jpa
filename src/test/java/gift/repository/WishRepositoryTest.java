package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.repository.fixture.MemberFixture;
import gift.repository.fixture.ProductFixture;
import gift.repository.fixture.WishFixture;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WishRepositoryTest {
    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("Find Wishes By Member ID Test")
    void findByMemberId() {
        // given
        Member member = MemberFixture.createMember("user@example.com", "password");
        memberRepository.save(member);
        Product product = ProductFixture.createProduct("test",100,"kkk");
        productRepository.save(product);

        Wish expected = WishFixture.createWish(member,product,5);
        wishRepository.save(expected);

        // when
        List<Wish> actuals = wishRepository.findByMemberId(member.getId()).get();

        // then
        assertAll(
            () -> assertThat(actuals.size()).isEqualTo(1),
            () -> assertThat(actuals.get(0).getProduct().getName()).isEqualTo(expected.getProduct().getName())
        );
    }

    @Test
    @DisplayName("Find Wish By Member ID And Product Name Test")
    void findByMemberIdAndProductName() {
        // given
        Member member = MemberFixture.createMember("user@example.com", "password");
        memberRepository.save(member);
        Product product = ProductFixture.createProduct("test",100,"kkk");
        productRepository.save(product);

        Wish expected = WishFixture.createWish(member,product,5);
        wishRepository.save(expected);

        // when
        Wish actual = wishRepository.findByMemberIdAndProductId(member.getId(),product.getId()).get();

        // then
        assertAll(
            () -> assertThat(actual.getMember().getId()).isEqualTo(expected.getMember().getId()),
            () -> assertThat(actual.getProduct().getName()).isEqualTo(expected.getProduct().getName())
        );
    }
}