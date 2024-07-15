package gift.service.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.domain.member.Member;
import gift.domain.member.MemberRepository;
import gift.domain.product.Product;
import gift.domain.product.ProductReposiotory;
import gift.domain.wish.Wish;
import gift.domain.wish.WishRepository;
import gift.web.dto.MemberDto;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberServiceTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductReposiotory productReposiotory;
    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        memberRepository.save(new Member("wjdghtjd06@naver.com", "1234"));
        memberRepository.save(new Member("ghtlr0506@naver.com", "5678"));
    }

    @Test
    void update() {
        Member member = memberRepository.findByEmail("wjdghtjd06@naver.com").get();
        member.updateMember("ghtlr0607@naver.com", "5678");
        Member member2 = memberRepository.findByEmail("ghtlr0607@naver.com").get();
        assertThat(member2).isNotNull();
    }

    @Test
    void test() {
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            members.add(new Member("정호성" + i, "1234"));
        }
        memberRepository.saveAll(members);

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            products.add(new Product("상품" + i, 2000L, "image.url"));
        }
        productReposiotory.saveAll(products);

        List<Wish> wishess = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Wish wish = new Wish(members.get(i), products.get(i), 2L);
            wishess.add(wish);
        }
        wishRepository.saveAll(wishess);

        entityManager.clear();

        System.out.println("-----------N + 1 문제 테스트------------");
        List<Wish> everyWishes = wishRepository.findAll();
        assertFalse(everyWishes.isEmpty());
        System.out.println("-----------N + 1 문제 테스트22222-----------");
        List<Product> everyProducts = productReposiotory.findAll();
        assertFalse(everyProducts.isEmpty());
        System.out.println("-----------N + 1 문제 테스트33333-----------");
        List<Member> everyMembers = memberRepository.findAll();
        assertFalse(everyMembers.isEmpty());

    }
}