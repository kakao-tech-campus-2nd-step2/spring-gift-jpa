package gift.Login.service;

import gift.Login.model.Product;
import gift.Login.model.Wishlist;

import java.util.UUID;

public interface WishlistService {
    Wishlist getWishlistByMemberId(Long memberId);
    void addProductToWishlist(Long memberId, Product product);
    void updateProductInWishlist(Long memberId, Long productId, Product product);
    void removeProductFromWishlist(Long memberId, Long productId);
}
