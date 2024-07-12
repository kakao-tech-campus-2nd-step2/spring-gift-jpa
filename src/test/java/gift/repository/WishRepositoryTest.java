package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
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

    @Autowired
    private ProductRepository productRepository;

    private Member member;
    private Product product;

    @BeforeEach
    public void setUp() {
        member = memberRepository.save(new Member("test@email.com", "test1234"));
        product = productRepository.save(new Product("productName", 10000, "image.jpg"));
    }

    @Test
    void saveWishTest() {
        // given
        Wish wish = new Wish(product, member);

        // when
        Wish savedWish = wishRepository.save(wish);

        // then
        assertThat(savedWish).isNotNull();
        assertThat(savedWish).isEqualTo(wish);
    }

    @Test
    void findWishByProductIdTest() {
        // given
        Wish wish = wishRepository.save(new Wish(product, member));

        // when
        var wishList = wishRepository.findByProductId(product.getId());

        // then
        assertThat(wishList).hasSize(1);
        assertThat(wishList.getFirst()).isEqualTo(wish);
    }

    @Test
    void findWishByMemberTest() {
        // given
        Wish wish = wishRepository.save(new Wish(product, member));

        // when
        var wishList = wishRepository.findByMember(member);

        // then
        assertThat(wishList).hasSize(1);
        assertThat(wishList.getFirst()).isEqualTo(wish);
    }

    @Test
    void deleteWishByMemberIdAndIdTest() {
        // given
        Wish wish = wishRepository.save(new Wish(product, member));

        // when
        int deletedCount = wishRepository.deleteByIdAndMember(wish.getId(), member);

        // then
        assertThat(deletedCount).isEqualTo(1);
        assertThat(wishRepository.findById(wish.getId())).isEmpty();
    }
}