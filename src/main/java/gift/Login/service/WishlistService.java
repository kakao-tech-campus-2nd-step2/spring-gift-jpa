package gift.Login.service;

import gift.Login.model.Product;
import gift.Login.model.Wishlist;

import java.util.UUID;

public interface WishlistService {
    Wishlist getWishlistByMemberId(UUID memberId);
    void addProductToWishlist(UUID memberId, Product product);
    void updateProductInWishlist(UUID memberId, Long productId, Product product);
    void removeProductFromWishlist(UUID memberId, Long productId);
}
