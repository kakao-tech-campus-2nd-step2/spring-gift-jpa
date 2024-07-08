package gift.repository;

import gift.entity.Wish;

import java.util.List;

public interface WishRepository {
    void addToWishlist(String email, String type, long productId);
    void removeFromWishlist(String email, String type, long productId);
    List<Wish> getWishlistItems(String email);
}
