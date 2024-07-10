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

import java.util.List;

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
        Product product = new Product("name", 134, "asdf");
        Member member = new Member("asdfasdf@naver.com", "asdfasdf");
        Wish wish = new Wish(1L, product, member);
        memberRepository.save(member);
        productRepository.save(product);
        Wish real = wishRepository.save(wish);
        assertAll(
                () -> assertThat(real.getId()).isNotNull(),
                () -> assertThat(real.getMember().getId()).isEqualTo(wish.getMember().getId()),
                () -> assertThat(real.getProduct().getId()).isEqualTo(wish.getProduct().getId())
        );
    }

    @Test
    @DisplayName("멤버를 이메일로 조회")
    void memberRepoFindByEmail() {
        String expected = "asdfasdf@naver.com";
        Member member = new Member(1L, expected, "asdfasdf");
        memberRepository.save(member);
        String actual = memberRepository.findByEmail(expected).get().getEmail();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("위시 리스트 ")
    public void testFindAllByMemberId() {
        Long memberId = 1L;
        Member member = new Member(1L, "asdfasdf@naver.com", "asdfasdf");
        Product product1 = new Product(1L, "Product 1", 100, "image1.jpg");
        Product product2 = new Product(2L, "Product 2", 200, "image2.jpg");
        Wish wish1 = new Wish(product1, member);
        Wish wish2 = new Wish(product2, member);

        memberRepository.save(member);
        productRepository.save(product1);
        productRepository.save(product2);
        wishRepository.save(wish1);
        wishRepository.save(wish2);

        List<Product> products = wishRepository.findAllByMemberId(memberId);

        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(2);

        for (Product product : products) {
            assertThat(product.getId()).isNotNull();
            assertThat(product.getName()).isNotBlank();
            assertThat(product.getPrice()).isGreaterThan(99);
            assertThat(product.getImageUrl()).isNotBlank();
        }
    }
}
