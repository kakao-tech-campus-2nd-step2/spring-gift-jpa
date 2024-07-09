package gift.service;

import gift.repository.WishlistDao;
import gift.vo.WishProduct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistDao wishlistDao;

    public WishlistService(WishlistDao wishlistDao) {
        this.wishlistDao = wishlistDao;
    }

    public List<WishProduct> getWishProductLost(String memberEmail) {
        return wishlistDao.getWishProductList(memberEmail);
    }

    public Boolean addWishProduct(WishProduct wishProduct) {
        return wishlistDao.addWishProduct(wishProduct.getMemberEmail(), wishProduct.getProductId());
    }

    public Boolean deleteWishProduct(String memberEmail, Long productId) {
        return wishlistDao.deleteWishProduct(memberEmail, productId);
    }

}
