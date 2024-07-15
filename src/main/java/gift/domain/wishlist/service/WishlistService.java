package gift.domain.wishlist.service;

import gift.domain.product.dao.ProductJpaRepository;
import gift.domain.product.entity.Product;
import gift.domain.user.entity.User;
import gift.domain.wishlist.dao.WishlistJpaRepository;
import gift.domain.wishlist.dto.WishItemDto;
import gift.domain.wishlist.entity.WishItem;
import gift.exception.InvalidProductInfoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WishlistService {

    private final WishlistJpaRepository wishlistJpaRepository;
    private final ProductJpaRepository productJpaRepository;

    public WishlistService(WishlistJpaRepository wishlistJpaRepository, ProductJpaRepository productJpaRepository) {
        this.wishlistJpaRepository = wishlistJpaRepository;
        this.productJpaRepository = productJpaRepository;
    }

    public WishItem create(WishItemDto wishItemDto, User user) {
        Product product = productJpaRepository.findById(wishItemDto.productId())
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));

        WishItem wishItem = wishItemDto.toWishItem(user, product);
        WishItem savedWishItem = wishlistJpaRepository.save(wishItem);
        user.addWishItem(savedWishItem);

        return savedWishItem;
    }

    public Page<WishItem> readAll(Pageable pageable, User user) {
        return wishlistJpaRepository.findAllByUserId(user.getId(), pageable);
    }

    public void delete(long wishItemId) {
        WishItem wishItem = wishlistJpaRepository.findById(wishItemId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));

        User user = wishItem.getUser();
        user.removeWishItem(wishItem);
        wishlistJpaRepository.delete(wishItem);
    }

    public void deleteAllByUserId(User user) {
        user.removeWishlist();
        wishlistJpaRepository.deleteAllByUserId(user.getId());
    }

    public void deleteAllByProductId(long productId) {
        wishlistJpaRepository.deleteAllByProductId(productId);
    }
}
