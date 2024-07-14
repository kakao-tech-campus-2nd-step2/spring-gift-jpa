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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishJpaRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findByMemberAndProduct() {
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
    
}
