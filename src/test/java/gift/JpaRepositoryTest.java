package gift;

import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class JpaRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WishRepository wishRepository;

    @Test
    @DisplayName("멤버 저장 테스트")
    void saveMember() {
        Member member = new Member(1L, "asdfasdf@naver.com", "asdfasdf");
        Member real = memberRepository.save(member);
        assertAll(
                () -> assertThat(real.getId()).isNotNull(),
                () -> assertThat(real.getEmail()).isEqualTo(member.getEmail()),
                () -> assertThat(real.getPassword()).isEqualTo(member.getPassword())
        );
    }

    @Test
    @DisplayName("상품 저장 테스트")
    void saveProduct() {
        Product product = new Product(1L, "name", 134, "asdf");
        Product real = productRepository.save(product);
        assertAll(
                () -> assertThat(real.getId()).isNotNull(),
                () -> assertThat(real.getName()).isEqualTo(product.getName()),
                () -> assertThat(real.getImageUrl()).isEqualTo(product.getImageUrl()),
                () -> assertThat(real.getPrice()).isEqualTo(product.getPrice())
        );
    }

    @Test
    @DisplayName("위시 저장 테스트")
    void saveWish() {
        Product product = new Product(1L, "name", 134, "asdf");
        Member member = new Member(1L, "asdfasdf@naver.com", "asdfasdf");
        Wish wish = new Wish(1L, 1L, 1L);
        Wish real = wishRepository.save(wish);
        assertAll(
                () -> assertThat(real.getId()).isNotNull(),
                () -> assertThat(real.getmemberId()).isEqualTo(wish.getmemberId()),
                () -> assertThat(real.getProductId()).isEqualTo(wish.getProductId())
        );
    }

    @Test
    void memberRepoFindByEmail() {
        String expected = "asdfasdf@naver.com";
        Member member = new Member(1L, expected, "asdfasdf");
        memberRepository.save(member);
        String actual = memberRepository.findByEmail(expected).get().getEmail();
        assertThat(actual).isEqualTo(expected);
    }
}
