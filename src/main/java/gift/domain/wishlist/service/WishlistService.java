package gift.domain.wishlist.service;

import gift.domain.product.dao.ProductJpaRepository;
import gift.domain.product.entity.Product;
import gift.domain.user.entity.User;
import gift.domain.wishlist.dao.WishlistJpaRepository;
import gift.domain.wishlist.dto.WishItemDto;
import gift.domain.wishlist.entity.WishItem;
import gift.exception.InvalidProductInfoException;
import java.util.List;
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

        return wishlistJpaRepository.save(wishItem);
    }

    public List<WishItem> readAll(User user) {
        return wishlistJpaRepository.findAllByUserId(user.getId());
    }

    public void delete(long wishlistId) {
        WishItem wishItem = wishlistJpaRepository.findById(wishlistId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));

        wishlistJpaRepository.delete(wishItem);
    }
}
