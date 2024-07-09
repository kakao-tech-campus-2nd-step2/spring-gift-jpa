package gift.service;

import gift.domain.Wishlist;
import gift.repository.WishlistJdbcRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {
    private final WishlistJdbcRepository wishlistRepository;
    private final ProductService productService;

    public WishlistService(WishlistJdbcRepository wishlistRepository, ProductService productService) {
        this.wishlistRepository = wishlistRepository;
        this.productService = productService;
    }

    public List<Wishlist> getWishlist(Long userId) {
        return wishlistRepository.findWishList(userId);
    }

    public boolean addWishlist(Wishlist wishlist){
        if(productService.getProduct(wishlist.getProductId()) == null){

            return false;
        }

        int result = wishlistRepository.addWishItem(wishlist);
        return result == 1;
    }

    public boolean updateWishlistItem(Wishlist wishlist){
        if(productService.getProduct(wishlist.getProductId()) == null){
            return false;
        }
        Wishlist wishlistItem = new Wishlist(wishlist.getUserId(),wishlist.getProductId(),wishlist.getQuantity());
        return wishlistRepository.updateWishlistItem(wishlistItem) == 1;
    }

    public boolean deleteWishlist(Long userId, Long productId){
        return wishlistRepository.deleteWishItem(userId, productId) == 1;
    }

}
