package gift.wish.service;

import gift.member.model.Member;
import gift.product.model.Product;
import gift.wish.model.Wish;
import gift.wish.model.WishDTO;

import java.util.List;

public interface WishService {
    void addProductToWishlist(Member member, Long productId);
    List<WishDTO> getWishlistByMemberId(Member member);
    void deleteWish(Long wishId);
}
