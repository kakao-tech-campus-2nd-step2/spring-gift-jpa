package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.member.Member;
import gift.model.member.Role;
import gift.model.product.Product;
import gift.model.wish.Wish;
import gift.repository.member.MemberRepository;
import gift.repository.product.ProductRepository;
import gift.repository.wish.WishRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
public class WishJpaRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("멤버와 상품으로 찜 조회")
    void findByMemberAndProduct() {
        // given
        Member member = new Member(null, "member1@asd.com", "asd", "asd", Role.USER);
        Product product = new Product(null, "product1", 1000, "product1.jpg");
        Wish wish = new Wish(null, member, product, 2L);

        memberRepository.save(member);
        productRepository.save(product);
        wishRepository.save(wish);

        // when
        Wish findWish = wishRepository.findByMemberAndProduct(member, product).get();

        // then
        assertAll(
            () -> assertThat(findWish.getMember().getEmail()).isEqualTo("member1@asd.com"),
            () -> assertThat(findWish.getProduct().getName()).isEqualTo("product1"),
            () -> assertThat(findWish.getCount()).isEqualTo(2L)
        );
    }

    @Test
    @DisplayName("멤버로 찜 조회")
    void findAllByMemberByIdDesc() {
        // given
        Member member1 = new Member(null, "test1.com", "test1", "test1", Role.USER);
        Member member2 = new Member(null, "test2.com", "test2", "test2", Role.USER);
        Product product1 = new Product(null, "product1", 1000, "product1.jpg");
        Product product2 = new Product(null, "product2", 2000, "product2.jpg");
        Wish wish1 = new Wish(null, member1, product1, 2L);
        Wish wish2 = new Wish(null, member1, product2, 3L);
        Wish wish3 = new Wish(null, member2, product1, 4L);

        memberRepository.save(member1);
        memberRepository.save(member2);
        productRepository.save(product1);
        productRepository.save(product2);
        wishRepository.save(wish1);
        wishRepository.save(wish2);
        wishRepository.save(wish3);

        // when
        Page<Wish> response = wishRepository.findAllByMemberByIdDesc(member1.getId(),
            PageRequest.of(0, 10));

        // then
        assertThat(response.getContent().size()).isEqualTo(2);
    }

}
