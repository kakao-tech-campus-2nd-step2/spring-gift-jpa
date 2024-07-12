package gift.product.application;

import gift.product.domain.Product;
import gift.product.domain.WishList;
import gift.product.infra.ProductRepository;
import gift.product.infra.WishListRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;

    public WishListService(WishListRepository wishListRepository, ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
    }

    public WishList getWishListByUserId(Long userId) {
        return wishListRepository.findByUserId(userId);
    }


    public WishList getProductsInWishList(Long userId) {
        return wishListRepository.findByUserId(userId);
    }


    @Transactional
    public void addProductToWishList(Long userId, Long productId) {
        WishList wishList = wishListRepository.findByUserId(userId);
        Product product = productRepository.findById(productId).orElseThrow();

        if (wishList == null) {
            wishList = new WishList();
            wishList = wishListRepository.save(wishList);
        }
        product.setWishList(wishList);
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
