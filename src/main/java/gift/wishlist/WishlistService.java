package gift.wishlist;

import gift.member.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private final WishlistDao wishlistDao;

    public WishlistService(WishlistDao wishlistDao) {
        this.wishlistDao = wishlistDao;
    }

    public void postWishlist(Member member, Long productId) {
        wishlistDao.insertWish(member,productId);
    }

    public List<Long> getAllWishlist() {
        return wishlistDao.findAllWish();
    }

    public Optional<Long> getWishlistById(Long productId) {
        return wishlistDao.findProductById(productId);
    }

    public void deleteWishlist(Long productId) {
        wishlistDao.deleteWish(productId);
    }
}
