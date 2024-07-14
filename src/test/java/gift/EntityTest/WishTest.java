package gift.EntityTest;

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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class WishTest {
    @Autowired
    WishRepository wishRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TestEntityManager testEntityManager;

    private Member member1;
    private Member member2;
    private Product product1;
    private Product product2;

    @BeforeEach
    void beforEach() {
        product1 = productRepository.save(new Product("아메리카노", 4000, "아메리카노url"));
        product2 = productRepository.save(new Product("카푸치노", 4500, "카푸치노url"));
        member1 = memberRepository.save(new Member("woo6388@naver.com", "12345678"));
        member2 = memberRepository.save(new Member("qoo6388@naver.com", "0000"));
        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    void CreationTest(){
        Wish wish = new Wish(member1, product1, 1);
        Wish actual = wishRepository.save(wish);

        assertAll(
                ()->assertThat(actual.getMember()).isEqualTo(member1),
                ()-> assertThat(actual.getProduct()).isEqualTo(product1),
                ()->assertThat(actual.getCount()).isEqualTo(1)
        );
    }

    @Test
    void SetterTest(){
        Wish wish = new Wish(member1, product1, 1);
        Wish actual = wishRepository.save(wish);

        actual.setMember(member2);
        actual.setProduct(product2);
        actual.setCount(2);

        assertAll(
                () -> assertThat(actual.getMember()).isEqualTo(member2),
                () -> assertThat(actual.getProduct()).isEqualTo(product2),
                () -> assertThat(actual.getCount()).isEqualTo(2)
        );
    }


}
