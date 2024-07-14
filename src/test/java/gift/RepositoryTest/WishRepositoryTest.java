package gift.RepositoryTest;

import gift.Model.Member;
import gift.Model.Product;
import gift.Model.Wish;
import gift.Repository.MemberRepository;
import gift.Repository.ProductRepository;
import gift.Repository.WishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class WishRepositoryTest {
    @Autowired
    WishRepository wishRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TestEntityManager testEntityManager;

    private Member member;
    private Product product1;
    private Product product2;

    @BeforeEach
    void beforEach(){
        product1 = productRepository.save(new Product("아메리카노", 4000, "아메리카노url"));
        product2 = productRepository.save(new Product("카푸치노", 4500, "카푸치노url"));
        member = memberRepository.save(new Member("woo6388@naver.com", "12345678"));
        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    void saveTest() {
        Wish wish = new Wish(member, product1, 5);
        assertThat(wish.getId()).isNull();
        var actual = wishRepository.save(wish);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void findByMemberTest() {
        Wish wish1 = wishRepository.save(new Wish(member, product1, 1));
        Wish wish2 = wishRepository.save(new Wish(member, product2, 2));
        List<Wish> actual = wishRepository.findWishListByMember(member);
        assertAll(
                ()->assertThat(actual.get(0).getProduct().getName()).isEqualTo("아메리카노"),
                ()->assertThat(actual.get(0).getCount()).isEqualTo(1),
                ()->assertThat(actual.get(1).getProduct().getName()).isEqualTo("카푸치노"),
                ()->assertThat(actual.get(1).getCount()).isEqualTo(2)
        );
    }

    @Test
    void findByMemberAndProductTest() {
        Wish wish1 = wishRepository.save(new Wish(member, product1, 1));
        Wish wish2 = wishRepository.save(new Wish(member, product2, 2));
        Optional<Wish> actual = wishRepository.findByMemberAndProduct(member, product1);
        assertAll(
                ()->assertThat(actual).isPresent(),
                ()->assertThat(actual.get().getCount()).isEqualTo(1),
                ()->assertThat(actual.get().getId()).isEqualTo(wish1.getId())
        );
    }

    @Test
    void updateTest() {
        Wish wish1 = wishRepository.save(new Wish(member, product1, 1));
        Optional<Wish> optionalWish = wishRepository.findById(wish1.getId());
        Wish wish = optionalWish.get();
        wish.setCount(5);

        var actual = wishRepository.findById(wish.getId());
        assertThat(actual.get().getCount()).isEqualTo(5);
    }

    @Test
    void deleteTest() {
        Wish wish1 = wishRepository.save(new Wish(member, product1, 1));
        Optional<Wish> optionalWish = wishRepository.findById(wish1.getId());
        wishRepository.deleteById(optionalWish.get().getId());
        Optional<Wish> actual = wishRepository.findById(optionalWish.get().getId());
        assertThat(actual).isEmpty();
    }
}
