package gift.wish.service;

import gift.member.model.Member;
import gift.product.model.Product;
import gift.wish.model.Wish;

import java.util.List;

public interface WishService {
    void addProductToWishlist(Member member, Product product);
    List<Wish> getWishlistByMemberId(Member member);
    void updateProductInWishlist(Long memberId, Long productId, Product product);
    void removeProductFromWishlist(Long memberId, Long productId);
}
