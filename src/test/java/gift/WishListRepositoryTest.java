package gift;

import gift.Entity.Users;
import gift.Entity.Wishlist;
import gift.Entity.WishlistId;
import gift.Model.User;
import gift.Model.WishListItem;
import gift.Repository.UsersJpaRepository;
import gift.Repository.WishListJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
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

        assertThat(wishListJpaRepository.findByIdUserId(savedUser.getId())).contains(savedwishlist);

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
    public void testRemoveWishlistItem() {
        WishListItem wishlistItem = new WishListItem(1L, 1L, 1, 0, "test", 1000);
        Wishlist wishlist = Wishlist.createWishlist(wishlistItem);
        Wishlist savedwishlist = wishListJpaRepository.save(wishlist);

        wishListJpaRepository.delete(savedwishlist);

        assertThat(wishListJpaRepository.findById(new WishlistId(savedwishlist.getUserId(), savedwishlist.getProductId()))).isEmpty();

    }
}
