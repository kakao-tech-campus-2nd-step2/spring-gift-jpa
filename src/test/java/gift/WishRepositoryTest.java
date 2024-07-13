package gift;

import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        Member member = Member.builder()
                .email("test@example.com")
                .password("password123")
                .build();
        memberRepository.save(member);

        Product product = Product.builder()
                .name("열라면")
                .price(1600)
                .imageurl("https://i.namu.wiki/i/fuvd7qkb8P6PA_sD5ufjgpKUhRgxxTrIWnkPIg5H_UAPMUaArn1U1DweD7T_f_8RVxTDjqaiFwKr-quURwc_eQ.webp")
                .build();
        productRepository.save(product);

        Wish wish = Wish.builder()
                .member(member)
                .product(product)
                .build();

        Wish savedWish = wishRepository.save(wish);

        assertThat(savedWish.getId()).isNotNull();
        assertThat(savedWish.getMember().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void findByMemberId() {
        Member member = Member.builder()
                .email("test@example.com")
                .password("password123")
                .build();
        Member savedMember = memberRepository.save(member);

        Product product = Product.builder()
                .name("열라면")
                .price(1600)
                .imageurl("https://i.namu.wiki/i/fuvd7qkb8P6PA_sD5ufjgpKUhRgxxTrIWnkPIg5H_UAPMUaArn1U1DweD7T_f_8RVxTDjqaiFwKr-quURwc_eQ.webp")
                .build();
        productRepository.save(product);

        Wish wish = Wish.builder()
                .member(savedMember)
                .product(product)
                .build();
        wishRepository.save(wish);

        Pageable pageable = PageRequest.of(0, 5);
        Page<Wish> wishesPage = wishRepository.findByMemberId(savedMember.getId(), pageable);
        List<Wish> wishes = wishesPage.getContent();

        assertThat(wishes).isNotEmpty();
        assertThat(wishes.get(0).getMember().getEmail()).isEqualTo("test@example.com");
    }
}