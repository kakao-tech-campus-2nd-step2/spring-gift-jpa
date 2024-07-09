package gift.wish.repository;

import gift.member.domain.*;
import gift.member.repository.MemberRepository;
import gift.product.domain.Product;
import gift.product.domain.ProductName;
import gift.product.domain.ProductPrice;
import gift.product.repository.ProductRepository;
import gift.wish.domain.ProductCount;
import gift.wish.domain.Wish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    private Member member;
    private Product product;
    private Wish wish;

    @BeforeEach
    void beforeEach() {
        member = memberRepository.save(new Member(null, MemberType.USER, new Email("email"), new Password("password"), new Nickname("nickname")));
        product = productRepository.save(new Product(null, new ProductName("name"), new ProductPrice(10L), "imageUrl"));
        wish = new Wish(null, member.getId(), product.getId(), new ProductCount(10L));
    }

    @Test
    @Description("save 테스트")
    void saveTest() {
        // given
        // when
        Wish savedWish = wishRepository.save(wish);

        // then
        assertThat(savedWish.getId()).isNotNull();
        assertThat(savedWish.getMemberId()).isEqualTo(wish.getMemberId());
        assertThat(savedWish.getProductId()).isEqualTo(wish.getProductId());
        assertThat(savedWish.getProductCount()).isEqualTo(wish.getProductCount());
    }

    @Test
    @Description("findById 테스트")
    void findByIdTest() {
        // given
        Wish savedWish = wishRepository.save(wish);

        // when
        Optional<Wish> foundWish = wishRepository.findById(savedWish.getId());

        // then
        assertThat(foundWish).contains(savedWish);
    }

    @Test
    @Description("findAllByMemberId 테스트")
    void findAllByMemberIdTest() {
        // given
        Wish wish1 = new Wish(null, member.getId(), product.getId(), new ProductCount(5L));
        Wish wish2 = new Wish(null, member.getId(), product.getId(), new ProductCount(15L));
        wish1 = wishRepository.save(wish1);
        wish2 = wishRepository.save(wish2);

        // when
        List<Wish> wishes = wishRepository.findAllByMemberId(member.getId());

        // then
        assertThat(wishes).contains(wish1, wish2);
    }



    @Test
    @Description("update 테스트")
    void updateTest() {
        // given
        Wish savedWish = wishRepository.save(wish);

        // when
        savedWish = new Wish(savedWish.getId(), savedWish.getMemberId(), savedWish.getProductId(), new ProductCount(20L));
        Wish updatedWish = wishRepository.save(savedWish);

        // then
        assertThat(updatedWish).isEqualTo(savedWish);
    }

    @Test
    @Description("deleteById 테스트")
    void deleteTest() {
        // given
        Wish savedWish = wishRepository.save(wish);

        // when
        wishRepository.deleteById(savedWish.getId());
        List<Wish> wishes = wishRepository.findAllByMemberId(member.getId());

        // then
        assertThat(wishes).isEmpty();
    }
}
