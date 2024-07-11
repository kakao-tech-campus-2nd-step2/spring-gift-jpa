package gift.repositoryTest;

import gift.model.Member;
import gift.model.Product;
import gift.model.WishList;
import gift.model.MemberRepository;
import gift.model.ProductRepository;
import gift.model.WishListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WishListRepositoryTests {

    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveAndFindWishList() {
        Member member = new Member(null, "jiu@gmail.com", "password123", null);
        memberRepository.save(member);
        Product product = new Product(null, "지우", 1000, "http://example.com/image.jpg");
        productRepository.save(product);
        WishList wishList = new WishList(null, member.getId(), product.getId());
        wishListRepository.save(wishList);

        List<WishList> foundWishLists = wishListRepository.findByMemberId(member.getId());
        assertThat(foundWishLists).hasSize(1);
    }

    @Test
    void testRemoveProductFromWishList() {
        Member member = new Member(null, "jiu@gmail.com", "password123", null);
        memberRepository.save(member);
        Product product = new Product(null, "지우", 1000, "http://example.com/image.jpg");
        productRepository.save(product);
        WishList wishList = new WishList(null, member.getId(), product.getId());
        wishListRepository.save(wishList);

        wishListRepository.deleteByMemberIdAndProductId(member.getId(), product.getId());
        List<WishList> foundWishLists = wishListRepository.findByMemberId(member.getId());
        assertThat(foundWishLists).isEmpty();
    }
}
