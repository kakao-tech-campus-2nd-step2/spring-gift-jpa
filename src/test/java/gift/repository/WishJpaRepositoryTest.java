package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.member.Member;
import gift.model.member.Role;
import gift.model.product.Product;
import gift.model.wish.Wish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishJpaRepositoryTest {

    @Autowired
    private WishJpaRepository wishJpaRepository;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Test
    public void findByMemberAndProduct() {
        // given
        Member member = new Member(null, "member1@asd.com", "asd", "asd", Role.USER);
        Product product = new Product(null, "product1", 1000, "product1.jpg");
        Wish wish = new Wish(null, member, product, 2L);

        memberJpaRepository.save(member);
        productJpaRepository.save(product);
        wishJpaRepository.save(wish);

        // when
        Wish findWish = wishJpaRepository.findByMemberAndProduct(member, product).get();

        // then
        assertAll(
            () -> assertThat(findWish.getMember().getEmail()).isEqualTo("member1@asd.com"),
            () -> assertThat(findWish.getProduct().getName()).isEqualTo("product1"),
            () -> assertThat(findWish.getCount()).isEqualTo(2L)
        );
    }

    @Test
    public void findByMemberId() {
        // given
        Member member = new Member(null, "member1@asd.com", "asd", "asd", Role.USER);
        Product product = new Product(null, "product1", 1000, "product1.jpg");
        Wish wish = new Wish(null, member, product, 2L);

        memberJpaRepository.save(member);
        productJpaRepository.save(product);
        wishJpaRepository.save(wish);

        // when
        assertAll(
            () -> assertThat(wishJpaRepository.findByMemberId(1L).get(0).getMember().getEmail())
                .isEqualTo("member1@asd.com"),
            () -> assertThat(wishJpaRepository.findByMemberId(1L).get(0).getProduct().getName()),
            () -> assertThat(wishJpaRepository.findByMemberId(1L).get(0).getCount()).isEqualTo(2L)
        );
    }
}
