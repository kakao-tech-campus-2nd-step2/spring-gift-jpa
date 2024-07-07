package gift.product.application;

import gift.product.domain.Product;
import gift.product.domain.WishList;
import gift.product.infra.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {

    @Autowired
    private WishListRepository wishListRepository;

    public WishList getWishListByUserId(Long userId) {
        return wishListRepository.findByUserId(userId);
    }

    public void addProductToWishList(Long userId, Product product) {
        WishList wishList = wishListRepository.findByUserId(userId);
        if (wishList == null) {
            wishList = new WishList();
            wishList.setUserId(userId);
            wishList = wishListRepository.save(wishList);
        }
        wishListRepository.addProductToWishList(wishList.getId(), product);
    }

    public List<Product> getProductsInWishList(Long userId) {
        WishList wishList = wishListRepository.findByUserId(userId);
        if (wishList != null) {
            return wishListRepository.findProductsByWishListId(wishList.getId());
        }
        return null;
    }


    public void deleteProductFromWishList(Long userId, Long productId) {
        WishList wishList = wishListRepository.findByUserId(userId);
        if (wishList != null) {
            wishListRepository.deleteProductFromWishList(wishList.getId(), productId);
        }
    }
}
