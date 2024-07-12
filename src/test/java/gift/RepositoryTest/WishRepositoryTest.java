package gift.RepositoryTest;

import gift.Model.Member;
import gift.Model.Product;
import gift.Model.ResponseWishDTO;
import gift.Model.Wish;
import gift.Repository.MemberRepository;
import gift.Repository.ProductRepository;
import gift.Repository.WishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class WishRepositoryTest {
    @Autowired
    WishRepository WishRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void beforEach(){
        productRepository.save(new Product("아메리카노", 4000, "아메리카노url"));
        productRepository.save(new Product("카푸치노", 4500, "카푸치노url"));
        member = memberRepository.save(new Member("woo6388@naver.com", "12345678"));
    }

    @Test
    void saveTest() {
        Long memberId=member.getId();
        Wish wish = new Wish(memberId, 1L, 5);
        assertThat(wish.getId()).isNull();
        var actual = WishRepository.save(wish);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void findWishsByEmailTest (){
        Long memberId = member.getId();
        Wish wish1 = WishRepository.save(new Wish(memberId, 1L, 1));
        Wish wish2 = WishRepository.save(new Wish(memberId, 2L, 2));
        List<ResponseWishDTO> actual = WishRepository.findWishsByMemberId(memberId);
        assertThat(actual.get(0).getName()).isEqualTo("아메리카노");
        assertThat(actual.get(0).getCount()).isEqualTo(1);
        assertThat(actual.get(1).getName()).isEqualTo("카푸치노");
        assertThat(actual.get(1).getCount()).isEqualTo(2);
    }

    @Test
    void findByEmailAndProductIdTest(){
        Long memberId = member.getId();
        Wish wish1 = WishRepository.save(new Wish(memberId, 1L, 1));
        Wish wish2 = WishRepository.save(new Wish(memberId, 2L, 2));
        Optional<Wish> actual = WishRepository.findByMemberIdAndProductId(memberId, 1L);
        assertThat(actual).isPresent();
        assertThat(actual.get().getCount()).isEqualTo(1);
        assertThat(actual.get().getId()).isEqualTo(wish1.getId());
    }


    @Test
    void updateTest() {
        Long memberId = member.getId();
        Wish wish1 = WishRepository.save(new Wish(memberId, 1L, 1));
        Optional<Wish> optionalWish = WishRepository.findById(wish1.getId());
        Wish wish = optionalWish.get();
        wish.setCount(5);

        var actual = WishRepository.findById(wish.getId());
        assertThat(actual.get().getCount()).isEqualTo(5);
    }



    @Test
    void deleteTest() {
        Long memberId = member.getId();
        Wish wish1 = WishRepository.save(new Wish(memberId, 1L, 1));
        Optional<Wish> optionalWish = WishRepository.findById(wish1.getId());
        assertThat(optionalWish).isPresent();
        WishRepository.deleteById(optionalWish.get().getId());
        Optional<Wish> actual = WishRepository.findById(optionalWish.get().getId());
        assertThat(actual).isEmpty();
    }


}
