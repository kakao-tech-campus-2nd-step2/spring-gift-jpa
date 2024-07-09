package gift.service;

import gift.domain.Wishlist;
import gift.repository.WishlistDao;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {
    private final WishlistDao wishlistDao;
    private final ProductService productService;

    public WishlistService(WishlistDao wishlistDao, ProductService productService) {
        this.wishlistDao = wishlistDao;
        this.productService = productService;
    }

    public List<Wishlist> getWishlistById(Long id) {
        return wishlistDao.findWishListByMemberId(id);
    }

    public void addWishItem(Wishlist wishlist) {
        Wishlist wishlistItem = new Wishlist(wishlist.getMemberId(),wishlist.getProductId(), wishlist.getQuantity());
        wishlistDao.addWishItem(wishlistItem);
    }

    public void updateWishItem(Wishlist wishlist) {
        Wishlist wishlistItem = new Wishlist(wishlist.getMemberId(),wishlist.getProductId(), wishlist.getQuantity());
        wishlistDao.updateWishItemQuanttity(wishlistItem);
    }

    public void deleteWishItem(Long memberId, Long productId) {
        wishlistDao.deleteWishItem(memberId, productId);
    }
}
