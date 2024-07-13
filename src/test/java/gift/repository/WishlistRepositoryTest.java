package gift.repository;

import gift.entity.Wishlist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Test
    public void save() {
        // given
        String testEmail = "test@naver.com";

        Wishlist wishlist = wishlistRepository.save(new Wishlist(testEmail));

        // when
        Wishlist expect = wishlistRepository.save(wishlist);

        // then
        assertThat(expect.getEmail()).isEqualTo(testEmail);
    }

    @Test
    public void find() {
        // given
        String testEmail = "test@naver.com";
        Wishlist wishlist = wishlistRepository.save(new Wishlist(testEmail));

        // when
        wishlistRepository.save(wishlist);
        Wishlist expect = wishlistRepository.findByEmail(testEmail).get();

        // then
        assertThat(expect.getEmail()).isEqualTo(testEmail);
    }
}
