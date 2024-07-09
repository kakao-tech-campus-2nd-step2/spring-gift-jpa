package gift;

import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WishRepositoryTest {
    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("위시리스트 추가 태스트")
    public void addWishListTest() {
        Member member = new Member("admin@gmail.com", "password");
        memberRepository.save(member);

        Product product = new Product("치킨", 20000, "chicken.com");
        productRepository.save(product);

        Wish wish = new Wish(member.getId(), product.getId());
        wishRepository.save(wish);

        List<Wish> wishList = wishRepository.findByMemberId(member.getId());
        assertThat(wishList.get(0).getProductId()).isEqualTo(product.getId());
    }

    @Test
    @DisplayName("위시리스트 삭제 테스트")
    public void deleteWishTest() {
        Member member = new Member("admin@gmail.com", "password");
        memberRepository.save(member);

        Product product = new Product("치킨", 20000, "chicken.com");
        productRepository.save(product);

        Wish wish = new Wish(member.getId(), product.getId());
        wishRepository.save(wish);

        wishRepository.delete(wish);

        List<Wish> wishList = wishRepository.findByMemberId(member.getId());
        assertThat(wishList).isEmpty();
    }


}
