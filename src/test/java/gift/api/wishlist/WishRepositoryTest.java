package gift.api.wishlist;

import static org.assertj.core.api.Assertions.assertThat;

import gift.api.member.Member;
import gift.api.member.MemberRepository;
import gift.api.member.Role;
import gift.api.product.Product;
import gift.api.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WishRepository wishRepository;

    @Test
    void deleteById() {
        Member member = new Member("member@test.com", "passWD12", Role.USER);
        Product product = new Product("product", 1000, "https://test/image");
        Wish wish = new Wish(member, product, 5);

        memberRepository.save(member);
        productRepository.save(product);
        wishRepository.save(wish);

        WishId wishId = new WishId(member.getId(), product.getId());
        wishRepository.deleteById(wishId);

        assertThat(wishRepository.findById(wishId)).isEmpty();
    }
}