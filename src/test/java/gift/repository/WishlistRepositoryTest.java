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

    private WishlistRepository wishlistRepository;
    private MemberRepository memberRepository;
    private ProductRepository productRepository;

    private Member savedMember;
    private Product savedProduct;

    @Autowired
    public WishlistRepositoryTest(WishlistRepository wishlistRepository,
        MemberRepository memberRepository, ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    @BeforeEach
    public void setUp() {
        Member member = new Member(4L, "kbm", "kbm@kbm", "mbk", "user");
        savedMember = memberRepository.save(member);
        Product product = new Product(1L, "상품", "100", "https://kakao");
        savedProduct = productRepository.save(product);
    }

    @Test
    public void testSaveWishlist() {

        Wishlist wishlist = new Wishlist(1L, savedMember.getEmail(), savedProduct.getId());

        Wishlist saved = wishlistRepository.save(wishlist);

        assertAll(
            () -> assertThat(saved.getId()).isNotNull(),
            () -> assertThat(saved.getMemberEmail()).isEqualTo("kbm@kbm"),
            () -> assertThat(saved.getProductId()).isEqualTo(1L)
        );
    }

    @Test
    public void testFindByMemberEmail() {
        Wishlist wishlist = new Wishlist(1L, savedMember.getEmail(), savedProduct.getId());
        wishlistRepository.save(wishlist);
        List<Wishlist> found = wishlistRepository.findByMemberEmail(savedMember.getEmail());
        assertAll(
            () -> assertThat(found.size()).isEqualTo(1),
            () -> assertThat(found.get(0).getMemberEmail()).isEqualTo(savedMember.getEmail())
        );
    }

    @Test
    void testDeleteByMemberEmailAndProductId() {
        Wishlist wishlist = new Wishlist(1L, savedMember.getEmail(), savedProduct.getId());
        wishlistRepository.save(wishlist);
        wishlistRepository.deleteByMemberEmailAndProductId(savedMember.getEmail(),
            savedProduct.getId());
        List<Wishlist> result = wishlistRepository.findByMemberEmail(savedMember.getEmail());
        assertThat(result.size()).isEqualTo(0);
    }
}