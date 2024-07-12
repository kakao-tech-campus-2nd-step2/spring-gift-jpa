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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class JpaWishTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WishRepository wishRepository;

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
    @DisplayName("위시 리스트 - 페이징")
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

        Pageable pageable = PageRequest.of(0, 2);

        Page<Product> products = wishRepository.findAllByMemberId(memberId, pageable);

        assertThat(products).isNotNull();
        assertThat(products.getTotalElements()).isEqualTo(2);

        for (Product product : products) {
            assertThat(product.getId()).isNotNull();
            assertThat(product.getName()).isNotBlank();
            assertThat(product.getPrice()).isGreaterThan(99);
            assertThat(product.getImageUrl()).isNotBlank();
        }
    }
}
