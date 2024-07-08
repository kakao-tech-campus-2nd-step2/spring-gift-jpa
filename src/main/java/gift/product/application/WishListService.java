package gift.product.application;

import gift.product.domain.Product;
import gift.product.domain.WishList;
import gift.product.infra.WishListRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public WishList getWishListByUserId(Long userId) {
        return wishListRepository.findByUserId(userId);
    }


    public WishList getProductsInWishList(Long userId) {
        WishList wishList = wishListRepository.findByUserId(userId);
        return wishList;
    }


    @Transactional
    public void addProductToWishList(Long userId, Product product) {
        WishList wishList = wishListRepository.findByUserId(userId);
        if (wishList == null) {
            wishList = new WishList();
            wishList.setUserId(userId);
            wishList = wishListRepository.save(wishList);
        }
        product.setWishList(wishList);  // Set the wishlist reference in the product
        wishList.getProducts().add(product);
        wishListRepository.save(wishList);
    }

    @Transactional
    public void deleteProductFromWishList(Long userId, Long productId) {
        WishList wishList = wishListRepository.findByUserId(userId);
        if (wishList != null) {
            wishList.getProducts().removeIf(product -> product.getId().equals(productId));
            wishListRepository.save(wishList);
        }
    }
}
