package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.Member;
import gift.model.Product;
import gift.model.Wishlist;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    private Member savedMember;
    private Product savedProduct;
    private Wishlist savedWishlist;

    @BeforeEach
    public void setUp() {
        Member member = new Member(4L, "kbm", "kbm@kbm", "mbk", "user");
        savedMember = memberRepository.save(member);
        Product product = new Product(1L, "상품", "100", "https://kakao");
        savedProduct = productRepository.save(product);
        savedWishlist = new Wishlist(1L, savedMember.getEmail(), savedProduct.getId());
    }

    @Test
    public void testSaveWishlist() {
        Wishlist saved = wishlistRepository.save(savedWishlist);
        assertAll(
            () -> assertThat(saved.getId()).isNotNull(),
            () -> assertThat(saved.getMemberEmail()).isEqualTo("kbm@kbm"),
            () -> assertThat(saved.getProductId()).isEqualTo(1L)
        );
    }

    @Test
    public void testFindByMemberEmail() {
        wishlistRepository.save(savedWishlist);
        List<Wishlist> found = wishlistRepository.findByMemberEmail(savedMember.getEmail());
        assertAll(
            () -> assertThat(found.size()).isEqualTo(1),
            () -> assertThat(found.get(0).getMemberEmail()).isEqualTo(savedMember.getEmail())
        );
    }

    @Test
    void testDeleteByMemberEmailAndProductId() {
        wishlistRepository.save(savedWishlist);
        wishlistRepository.deleteByMemberEmailAndProductId(savedMember.getEmail(),
            savedProduct.getId());
        List<Wishlist> result = wishlistRepository.findByMemberEmail(savedMember.getEmail());
        assertThat(result.size()).isEqualTo(0);
    }
}