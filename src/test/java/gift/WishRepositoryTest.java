package gift;

import static org.assertj.core.api.Assertions.assertThat;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WishRepositoryTest {

    private WishRepository wishRepository;

    private MemberRepository memberRepository;

    private ProductRepository productRepository;

    @Test
    void testSaveWish() {
        Member member = memberRepository.save(new Member.MemberBuilder()
            .email("test@example.com").password("password").build());
        Product product = productRepository.save(new Product.ProductBuilder().name("Product1")
            .price(BigDecimal.valueOf(10.00))
            .imageUrl("http://example.com/product1.jpg")
            .description("Description for Product1").build());
        Wish expected = new Wish.WishBuilder().member(member).product(product).build();
        Wish actual = wishRepository.save(expected);
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getMember().getEmail()).isEqualTo(member.getEmail());
        assertThat(actual.getProduct().getName()).isEqualTo(product.getName());
    }

    @Test
    void testFindByMemberId() {
        Member member = memberRepository.save(new Member.MemberBuilder()
            .email("test@example.com")
            .password("password")
            .build());
        Product product = productRepository.save(new Product.ProductBuilder().name("Product1")
            .price(BigDecimal.valueOf(10.00))
            .imageUrl("http://example.com/product1.jpg")
            .description("Description for Product1").build());
        wishRepository.save(new Wish.WishBuilder().member(member).product(product).build());

        List<Wish> wishes = wishRepository.findAllByMemberId(member.getId());
        assertThat(wishes).hasSize(1);
        assertThat(wishes.get(0).getMember().getEmail()).isEqualTo(member.getEmail());
    }
}