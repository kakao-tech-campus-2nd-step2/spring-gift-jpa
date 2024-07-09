package gift.member.business.service;

import gift.member.business.dto.WishListDto;
import gift.member.persistence.entity.Wishlist;
import gift.member.persistence.repository.MemberRepository;
import gift.member.persistence.repository.WishlistRepository;
import gift.member.presentation.dto.WishlistUpdateDto;
import gift.product.persistence.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public WishlistService(WishlistRepository wishlistRepository,
        ProductRepository productRepository, MemberRepository memberRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    public List<WishListDto> getWishLists(Long memberId) {
        var wishLists = wishlistRepository.getWishListByMemberId(memberId);

        return wishLists.stream()
            .map(wishlist -> WishListDto.of(
                wishlist.getId(),
                wishlist.getProduct(),
                wishlist.getCount()
            ))
            .toList();
    }

    public Long addWishList(Long memberId, Long productId) {
        var product = productRepository.getReferencedProduct(productId);
        var member = memberRepository.getReferencedMember(memberId);

        var wishList = new Wishlist(product, member, 1);
        return wishlistRepository.saveWishList(wishList);
    }

    public Long updateWishList(Long memberId, WishlistUpdateDto wishListUpdateDto) {
        var wishList = wishlistRepository.getWishListByMemberIdAndProductId(
            memberId, wishListUpdateDto.productId());

        wishList.setCount(wishListUpdateDto.count());
        return wishlistRepository.updateWishlist(wishList);
    }

    @Transactional
    public void deleteWishList(Long memberId, Long productId) {
        wishlistRepository.deleteWishlist(memberId, productId);
    }


}
