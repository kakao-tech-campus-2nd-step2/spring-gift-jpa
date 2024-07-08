package gift.wishlist.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WishlistJDBCRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("DELETE FROM wishlist");
        jdbcTemplate.execute("DELETE FROM member");
        jdbcTemplate.execute("DELETE FROM product");

        jdbcTemplate.execute("INSERT INTO member (email, password) VALUES ('test@example.com', 'password')");
        jdbcTemplate.execute("INSERT INTO member (email, password) VALUES ('test2@example.com', 'password')");
        jdbcTemplate.execute("INSERT INTO product (id, name, price, imageUrl) VALUES (1, 'Product', 1000, 'image')");
        jdbcTemplate.execute("INSERT INTO product (id, name, price, imageUrl) VALUES (2, 'Product', 1000, 'image')");
    }

    @Test
    public void 모든_위시리스트_조회_테스트() {
        // Given
        Wishlist wishlist1 = new Wishlist(null, "test@example.com", 1L);
        Wishlist wishlist2 = new Wishlist(null, "test2@example.com", 2L);
        wishlistRepository.addWishlist(wishlist1);
        wishlistRepository.addWishlist(wishlist2);

        // When
        List<Wishlist> wishlists = wishlistRepository.findByMemberEmail("test@example.com");

        // Then
        assertThat(wishlists).hasSize(1);
        assertThat(wishlists.get(0).getMemberEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void 위시리스트_ID로_조회_테스트() {
        // Given
        Wishlist wishlist = new Wishlist(null, "test@example.com", 1L);
        wishlistRepository.addWishlist(wishlist);
        Long wishlistId = wishlistRepository.findByMemberEmail("test@example.com").get(0).getId();

        // When
        Optional<Wishlist> foundWishlist = wishlistRepository.findById(wishlistId);

        // Then
        assertThat(foundWishlist).isPresent();
        assertThat(foundWishlist.get().getMemberEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void 위시리스트_ID로_조회_실패_테스트() {
        // Given
        Long wishlistId = 1L;

        // When
        Optional<Wishlist> foundWishlist = wishlistRepository.findById(wishlistId);

        // Then
        assertThat(foundWishlist).isNotPresent();
    }

    @Test
    public void 위시리스트_추가_테스트() {
        // Given
        Wishlist wishlist = new Wishlist(null, "test@example.com", 1L);

        // When
        wishlistRepository.addWishlist(wishlist);

        // Then
        List<Wishlist> wishlists = wishlistRepository.findByMemberEmail("test@example.com");
        assertThat(wishlists).hasSize(1);
        assertThat(wishlists.get(0).getMemberEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void 위시리스트_삭제_테스트() {
        // Given
        Wishlist wishlist = new Wishlist(null, "test@example.com", 1L);
        wishlistRepository.addWishlist(wishlist);
        Long wishlistId = wishlistRepository.findByMemberEmail("test@example.com").get(0).getId();

        // When
        wishlistRepository.deleteWishlist(wishlistId);

        // Then
        List<Wishlist> wishlists = wishlistRepository.findByMemberEmail("test@example.com");
        assertThat(wishlists).isEmpty();
    }
}
