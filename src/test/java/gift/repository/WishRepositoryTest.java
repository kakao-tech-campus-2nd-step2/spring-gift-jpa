package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
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
    private EntityManager entityManager;

    @Test
    @DisplayName("Find Wishes By Member ID Test")
    void findByMember() {
        // given
        Member member = new Member("user@example.com", "password");
        Product product = new Product("test",100,"kkk");
        memberRepository.save(member);
        productRepository.save(product);

        Wish wish = new Wish(member,product,5);
        wishRepository.save(wish);

        // when
        Optional<List<Wish>> wishes = wishRepository.findByMemberId(member.getId());

        // then
        List<Wish> wishList = wishes.get();
        assertAll(
            () -> assertThat(wishList.size()).isEqualTo(1),
            () -> assertThat(wishList.get(0).getProduct().getName()).isEqualTo(wish.getProduct().getName())
        );
    }

    @Test
    @DisplayName("Find Wish By Member ID And Product Name Test")
    void findByMemberAndProduct() {
        // given
        Member member = new Member("user@example.com", "password");
        Product product = new Product("test",100,"kkk");
        memberRepository.save(member);
        productRepository.save(product);

        Wish wish = new Wish(member,product,5);
        wishRepository.save(wish);
        entityManager.flush();
        entityManager.clear();

        // when
        Optional<Wish> expectedWish = wishRepository.findByMemberIdAndProductId(member.getId(),product.getId());

        // then
        Wish expected = expectedWish.get();
        assertAll(
            () -> assertThat(expected.getProduct().getName()).isEqualTo(wish.getProduct().getName())
        );
    }
}