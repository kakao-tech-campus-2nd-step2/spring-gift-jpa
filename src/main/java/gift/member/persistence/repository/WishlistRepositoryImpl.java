package gift.member.persistence.repository;

import gift.global.exception.ErrorCode;
import gift.global.exception.NotFoundException;
import gift.member.persistence.entity.Wishlist;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistRepositoryImpl implements WishlistRepository{
    private final WishlistJpaRepository wishlistJpaRepository;

    public WishlistRepositoryImpl(WishlistJpaRepository wishlistJpaRepository) {
        this.wishlistJpaRepository = wishlistJpaRepository;
    }

    @Override
    public List<Wishlist> getWishListByMemberId(Long memberId) {
        return wishlistJpaRepository.findByMemberId(memberId);
    }

    @Override
    public Long saveWishList(Wishlist wishList) {
        return wishlistJpaRepository.save(wishList).getId();
    }

    @Override
    public Wishlist getWishListByMemberIdAndProductId(Long memberId, Long productId) {
        return wishlistJpaRepository.findByMemberIdAndProductId(memberId, productId)
            .orElseThrow(() -> new NotFoundException(
                ErrorCode.DB_NOT_FOUND,
                "Wishlist with member id " + memberId + " and product id " + productId + " not found"
            ));
    }

    @Override
    public Long updateWishlist(Wishlist wishList) {
        return wishlistJpaRepository.save(wishList).getId();
    }

    @Override
    public void deleteWishlist(Long memberId, Long productId) {
        wishlistJpaRepository.deleteByMemberIdAndProductId(memberId, productId);
    }

    @Override
    public void deleteAll() {
        wishlistJpaRepository.deleteAll();
    }
}
