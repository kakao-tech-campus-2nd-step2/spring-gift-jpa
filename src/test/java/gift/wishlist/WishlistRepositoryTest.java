package gift.wishlist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.member.Member;
import gift.member.MemberRepository;
import gift.product.Product;
import gift.product.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

@DataJpaTest
class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setupMemberAndProduct() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.execute("TRUNCATE TABLE product RESTART IDENTITY");
        jdbcTemplate.execute("TRUNCATE TABLE member");
        jdbcTemplate.execute("TRUNCATE TABLE wishlist RESTART IDENTITY");
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");

        memberRepository.save(new Member("aaa@email.com", "password"));
        memberRepository.save(new Member("bbb@email.com", "password"));

        for (int i = 1; i < 4; i++) {
            productRepository.save(
                new Product(i, "product-" + i, i * 100, "product-" + i + "-image"));
        }
    }

    @Test
    @DisplayName("[Unit] getAllWishlists test")
    void getAllWishlistsTest() {
        //given
        List<Wishlist> expect = List.of(
            new Wishlist(1L, "aaa@email.com"),
            new Wishlist(2L, "aaa@email.com"),
            new Wishlist(3L, "aaa@email.com")
        );

        for (Wishlist wish : expect) {
            wishlistRepository.save(wish);
        }
        productRepository.save(new Product(4L, "product-4", 400, "product-4-image"));
        wishlistRepository.save(new Wishlist(4L, "bbb@email.com"));

        //when
        List<Wishlist> actual = wishlistRepository.findAllByMemberEmail("aaa@email.com");

        //then
        assertAll(
            () -> assertThat(actual).hasSize(expect.size()),
            () -> assertThat(actual.get(0)).isEqualTo(expect.get(0)),
            () -> assertThat(actual.get(1)).isEqualTo(expect.get(1)),
            () -> assertThat(actual.get(2)).isEqualTo(expect.get(2))
        );
    }

    @Test
    @DisplayName("[Unit] addWishlist test")
    void addWishlistTest() {
        //given
        Wishlist expect = new Wishlist(1L, "aaa@email.com");

        //when
        Wishlist actual = wishlistRepository.save(expect);

        //then
        assertThat(actual).isEqualTo(expect);
    }

    @Test
    @DisplayName("[Unit] deleteWishlist test")
    void deleteWishlistTest() {
        //given
        Wishlist expect = wishlistRepository.save(new Wishlist(1L, "aaa@email.com"));

        //when
        wishlistRepository.delete(expect);
        Optional<Wishlist> actual = wishlistRepository.findById(expect.getId());

        //then
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("[Unit] existWishlist test")
    void existWishlistTest() {
        //given
        Wishlist expect = new Wishlist(1L, "aaa@email.com");
        wishlistRepository.save(expect);

        //when
        Boolean trueCase = wishlistRepository.existsByMemberEmailAndProductId(
            expect.getMemberEmail(), expect.getProductId());
        Boolean falseCase = wishlistRepository.existsByMemberEmailAndProductId("aaa@email.com", 2L);

        //then
        assertAll(
            () -> assertThat(trueCase).isTrue(),
            () -> assertThat(falseCase).isFalse()
        );
    }
}