package gift;

import gift.Entity.Users;
import gift.Entity.Wishlist;
import gift.Model.User;
import gift.Model.WishListItem;
import gift.Repository.UsersJpaRepository;
import gift.Repository.WishListJpaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class WishListRepositoryTest {

    @Autowired
    private WishListJpaRepository wishListJpaRepository;

    @Autowired
    private UsersJpaRepository usersJpaRepository;

    @Test
    public void testGetWishlist() {
        User user1 = new User(1L, "1234@naver.com", "1234", "1234", false);
        Users users1 = Users.createUsers(user1);
        Users savedUser = usersJpaRepository.save(users1);

        WishListItem wishlistItem = new WishListItem(1L, 1L, 1, 0, "test", 1000);
        Wishlist wishlist = Wishlist.createWishlist(wishlistItem);
        Wishlist savedwishlist = wishListJpaRepository.save(wishlist);

        assertThat(savedwishlist.getUserId()).isEqualTo(wishlist.getUserId());
        assertThat(savedwishlist.getProductId()).isEqualTo(wishlist.getProductId());
        assertThat(savedwishlist.getCount()).isEqualTo(wishlist.getCount());
        assertThat(savedwishlist.getPrice()).isEqualTo(wishlist.getPrice());
        assertThat(savedwishlist.getProductName()).isEqualTo(wishlist.getProductName());
    }

    @Test
    public void testAddWishlistItem() {
        WishListItem wishlistItem = new WishListItem(1L, 1L, 1, 0, "test", 1000);
        Wishlist wishlist = Wishlist.createWishlist(wishlistItem);
        Wishlist savedwishlist = wishListJpaRepository.save(wishlist);

        assertThat(savedwishlist.getUserId()).isEqualTo(wishlist.getUserId());
        assertThat(savedwishlist.getProductId()).isEqualTo(wishlist.getProductId());
        assertThat(savedwishlist.getCount()).isEqualTo(wishlist.getCount());
        assertThat(savedwishlist.getPrice()).isEqualTo(wishlist.getPrice());
        assertThat(savedwishlist.getProductName()).isEqualTo(wishlist.getProductName());
    }

    @Test
    @DisplayName("delete가 정상적으로 이루어지는지")
    public void testRemoveWishlistItem() {
        WishListItem wishlistItem = new WishListItem(1L, 1L, 5, 0, "test", 1000);
        Wishlist wishlist = Wishlist.createWishlist(wishlistItem);
        Wishlist savedwishlist = wishListJpaRepository.save(wishlist);

        wishListJpaRepository.delete(savedwishlist);

        Optional<Wishlist> foundWishlist = wishListJpaRepository.findByWishlistId(savedwishlist.getUserId(), savedwishlist.getProductId());

        assertThat(foundWishlist).isEmpty();

    }

    @Test
    @DisplayName("Update가 정상적으로 이루어지는지")
    public void testUpdateWishlistItem() {
        WishListItem wishlistItem = new WishListItem(1L, 1L, 5, 0, "test", 5000);
        Wishlist wishlist = Wishlist.createWishlist(wishlistItem);
        wishListJpaRepository.save(wishlist);

        //수량을 5개에서 3개로 변경
        WishListItem updateItem = new WishListItem(1L, 1L, 3, 0, "test", 3000);
        Wishlist updateWishlist = Wishlist.createWishlist(updateItem);

        wishListJpaRepository.save(updateWishlist);

        Optional<Wishlist> foundWishlistOptional = wishListJpaRepository.findByWishlistId(updateWishlist.getUserId(), updateWishlist.getProductId());
        Wishlist foundWishlist = foundWishlistOptional.get();
        assertThat(foundWishlist.getUserId()).isEqualTo(updateWishlist.getUserId());
        assertThat(foundWishlist.getProductId()).isEqualTo(updateWishlist.getProductId());
        assertThat(foundWishlist.getCount()).isEqualTo(updateWishlist.getCount());
        assertThat(foundWishlist.getProductName()).isEqualTo(updateWishlist.getProductName());
        assertThat(foundWishlist.getPrice()).isEqualTo(updateWishlist.getPrice());

    }
}
