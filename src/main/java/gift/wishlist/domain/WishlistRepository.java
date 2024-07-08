package gift.wishlist.domain;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository {

    List<Wishlist> findByMemberEmail(String memberEmail);

    Optional<Wishlist> findById(Long wishlistId);

    void addWishlist(Wishlist wishlist);

    void deleteWishlist(Long wishlistId);

}
