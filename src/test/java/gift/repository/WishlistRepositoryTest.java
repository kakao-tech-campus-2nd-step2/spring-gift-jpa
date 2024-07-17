package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.Member;
import gift.model.Product;
import gift.model.Wishlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    private Wishlist saved;
    private Pageable pageable = PageRequest.of(0, 10);

    @BeforeEach
    public void setUp() {
        Member member = new Member(4L, "kbm", "kbm@kbm", "mbk", "user");
        savedMember = memberRepository.save(member);
        Product product = new Product(1L, "상품", "100", "https://kakao");
        savedProduct = productRepository.save(product);
        savedWishlist = new Wishlist(1L, savedMember, savedProduct);
        saved = wishlistRepository.save(savedWishlist);
    }

    @Test
    public void testSaveWishlist() {
        assertAll(
            () -> assertThat(saved.getId()).isNotNull(),
            () -> assertThat(saved.getMember().getEmail()).isEqualTo("kbm@kbm"),
            () -> assertThat(saved.getProduct().getId()).isEqualTo(1L)
        );
    }

    @Test
    public void testFindByMemberEmail() {
        Page<Wishlist> found = wishlistRepository.findByMember(savedMember, pageable);
        assertAll(
            () -> assertThat(found.getTotalElements()).isEqualTo(1),
            () -> assertThat(found.getContent().get(0).getMember().getEmail()).isEqualTo(
                savedMember.getEmail())
        );
    }

    @Test
    void testDeleteByMemberEmailAndProductId() {
        wishlistRepository.deleteByMemberAndProduct(savedMember, savedProduct);
        Page<Wishlist> result = wishlistRepository.findByMember(savedMember, pageable);
        assertThat(result.getTotalElements()).isEqualTo(0);
    }
}