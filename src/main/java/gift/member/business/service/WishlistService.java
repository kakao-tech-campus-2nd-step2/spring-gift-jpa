package gift.member.business.service;

import gift.member.business.dto.WishListDto;
import gift.member.persistence.entity.Wishlist;
import gift.member.persistence.repository.WishlistRepository;
import gift.member.presentation.dto.WishlistUpdateDto;
import gift.product.persistence.entity.Product;
import gift.product.persistence.repository.ProductRepository;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishlistRepository,
        ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    public List<WishListDto> getWishLists(Long memberId) {
        var wishLists = wishlistRepository.getWishListByMemberId(memberId);

        var productIds = wishLists.stream()
            .map(Wishlist::getProductId)
            .toList();

        Map<Long, Product> products = productRepository.getProductsByIds(productIds);

        return wishLists.stream()
            .map(wishlist -> WishListDto.of(
                wishlist.getId(),
                products.get(wishlist.getProductId()),
                wishlist.getCount()
            ))
            .toList();
    }

    public Long addWishList(Long memberId, Long productId) {
        var wishList = new Wishlist(productId, memberId, 1);
        return wishlistRepository.saveWishList(wishList);
    }

    public Long updateWishList(Long memberId, WishlistUpdateDto wishListUpdateDto) {
        var wishList = wishlistRepository.getWishListByMemberIdAndProductId(
            memberId, wishListUpdateDto.productId());

        wishList.setCount(wishListUpdateDto.count());
        return wishlistRepository.updateWishlist(wishList);
    }

    public void deleteWishList(Long memberId, Long productId) {
        wishlistRepository.deleteWishlist(memberId, productId);
    }


}
