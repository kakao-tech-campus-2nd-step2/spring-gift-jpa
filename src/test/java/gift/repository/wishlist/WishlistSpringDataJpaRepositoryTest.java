package gift.repository.wishlist;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.WishlistItem;
import gift.repository.member.MemberSpringDataJpaRepository;
import gift.repository.product.ProductSpringDataJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class WishlistSpringDataJpaRepositoryTest {

    @Autowired
    private WishlistSpringDataJpaRepository wishlistRepository;

    @Autowired
    private MemberSpringDataJpaRepository memberRepository;

    @Autowired
    private ProductSpringDataJpaRepository productRepository;

    @Test
    public void testAddItemToWishlist() {
        Member member = new Member("test@example.com", "password");
        memberRepository.save(member);

        Product product = new Product("Product 1", 100, "test-url");
        productRepository.save(product);

        WishlistItem item = new WishlistItem(member, product);
        wishlistRepository.save(item);

        List<WishlistItem> items = wishlistRepository.findByMemberId(member.getId());
        assertThat(items).isNotEmpty();
        assertThat(items.get(0).getProduct().getId()).isEqualTo(product.getId());
    }

    @Test
    public void testDeleteItemFromWishlist() {
        Member member = new Member("test2@example.com", "password");
        memberRepository.save(member);

        Product product = new Product("Product 2", 200, "test2-url");
        productRepository.save(product);

        WishlistItem item = new WishlistItem(member, product);
        wishlistRepository.save(item);

        wishlistRepository.delete(item);


        List<WishlistItem> items = wishlistRepository.findByMemberId(member.getId());
        assertThat(items).isEmpty();
    }

    @Test
    public void testGetWishlistByMemberId() {
        Member member = new Member("test3@example.com", "password");
        memberRepository.save(member);

        Product product1 = new Product("Product 3", 300, "test3-url");
        productRepository.save(product1);

        Product product2 = new Product("Product 4", 400, "test4-url");
        productRepository.save(product2);

        WishlistItem item1 = new WishlistItem(member, product1);
        WishlistItem item2 = new WishlistItem(member, product2);
        wishlistRepository.save(item1);
        wishlistRepository.save(item2);

        List<WishlistItem> items = wishlistRepository.findByMemberId(member.getId());
        assertThat(items).hasSize(2);
    }
}
