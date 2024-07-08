package gift.domain.wishlist.service;

import gift.domain.product.dao.ProductDao;
import gift.domain.product.entity.Product;
import gift.domain.user.entity.User;
import gift.domain.wishlist.dao.WishlistDao;
import gift.domain.wishlist.dto.WishItemDto;
import gift.domain.wishlist.entity.WishItem;
import gift.exception.InvalidProductInfoException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private final WishlistDao wishlistDao;
    private final ProductDao productDao;

    public WishlistService(WishlistDao wishlistDao, ProductDao productDao) {
        this.wishlistDao = wishlistDao;
        this.productDao = productDao;
    }

    public WishItem create(WishItemDto wishItemDto, User user) {
        Product product = productDao.findById(wishItemDto.productId())
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));

        WishItem wishItem = wishItemDto.toWishItem(user, product);

        return wishlistDao.insert(wishItem);
    }

    public List<WishItem> readAll(User user) {
        return wishlistDao.findAll(user);
    }

    public void delete(long wishlistId) {
        Integer nOfRowsAffected = wishlistDao.delete(wishlistId);

        if (nOfRowsAffected != 1) {
            throw new InvalidProductInfoException("error.invalid.product.id");
        }
    }
}
