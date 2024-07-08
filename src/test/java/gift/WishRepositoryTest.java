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
        Member member = new Member();
        member.setEmail("test@example.com");
        member.setPassword("password123");
        memberRepository.save(member);

        Product product = new Product();
        product.setName("신라면");
        product.setPrice(1500);
        product.setImageurl("https://image.nongshim.com/non/pro/1647822565539.jpg");
        productRepository.save(product);

        Wish wish = new Wish();
        wish.setMember(member);
        wish.setProduct(product);

        Wish savedWish = wishRepository.save(wish);

        assertThat(savedWish.getId()).isNotNull();
        assertThat(savedWish.getMember().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void findByMemberId() {
        Member member = new Member();
        member.setEmail("test@example.com");
        member.setPassword("password123");
        Member savedMember = memberRepository.save(member);

        Product product = new Product();
        product.setName("신라면");
        product.setPrice(1500);
        product.setImageurl("https://image.nongshim.com/non/pro/1647822565539.jpg");
        productRepository.save(product);

        Wish wish = new Wish();
        wish.setMember(savedMember);
        wish.setProduct(product);
        wishRepository.save(wish);

        List<Wish> wishes = wishRepository.findByMemberId(savedMember.getId());

        assertThat(wishes).isNotEmpty();
        assertThat(wishes.get(0).getMember().getEmail()).isEqualTo("test@example.com");
    }
}