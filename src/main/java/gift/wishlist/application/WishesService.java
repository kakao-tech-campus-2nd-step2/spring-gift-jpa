package gift.wishlist.application;

import gift.product.dao.ProductDao;
import gift.wishlist.dao.WishesDao;
import gift.product.domain.Product;
import gift.error.WishAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WishesService {

    private final WishesDao wishesDao;
    private final ProductDao productDao;

    public WishesService(WishesDao wishesDao, ProductDao productDao) {
        this.wishesDao = wishesDao;
        this.productDao = productDao;
    }

    public void addProductToWishlist(Long memberId, Long productId) {
        productDao.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("해당 상품은 존재하지 않습니다."));
        if (wishesDao.exists(memberId, productId)) {
            throw new WishAlreadyExistsException();
        }

        wishesDao.save(memberId, productId);
    }

    public void removeProductFromWishlist(Long memberId, Long productId) {
        if (!wishesDao.exists(memberId, productId)) {
            throw new NoSuchElementException("해당 상품은 위시 리스트에 존재하지 않습니다.");
        }

        wishesDao.remove(memberId, productId);
    }

    public List<Product> getWishlistOfMember(Long memberId) {
        return wishesDao.findWishlistOfMember(memberId);
    }

}
