package gift.product.application;

import gift.product.domain.Product;
import gift.product.domain.WishList;
import gift.product.domain.WishListProduct;
import gift.product.exception.ProductException;
import gift.product.infra.ProductRepository;
import gift.product.infra.WishListRepository;
import gift.util.ErrorCode;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    public Page<WishList> getProductsInWishList(Long userId, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        if (wishListRepository.findByUserId(userId, pageable).isEmpty()) {
            throw new ProductException(ErrorCode.WISHLIST_NOT_FOUND);
        }
        return wishListRepository.findByUserId(userId, pageable);
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

        WishListProduct wishListProduct = new WishListProduct(product, wishList);
        wishList.addWishListProduct(wishListProduct);

        wishListRepository.save(wishList);
    }

    @Transactional
    public void deleteProductFromWishList(Long userId, Long productId) {
        WishList wishList = wishListRepository.findByUserId(userId);
        if (wishList != null) {
            wishList.removeWishListProduct(productId);
        }
    }
}
