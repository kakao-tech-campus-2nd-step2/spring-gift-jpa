package gift.wishlist.service;

import gift.product.model.Product;
import gift.wishlist.model.WishList;
import gift.wishlist.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    @Autowired
    private WishListRepository wishListRepository;

    // id로 위시리스트 찾기
    public WishList findByUserId(Long userId) {
        return wishListRepository.findByUserId(userId);
    }

    // 위시리스트에 상품 추가
    public void addProductToWishList(Long userId, Product product) {
        wishListRepository.addProductToWishList(userId, product);
    }

    // 위시리스트에 상품 삭제
    public void removeProductFromWishList(Long userId, Long productId) {
        WishListRepository.removeProductFromWishList(userId, productId);
    }
}