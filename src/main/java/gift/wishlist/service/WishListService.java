package gift.wishlist.service;

import gift.product.model.Product;
import gift.wishlist.model.WishList;
import gift.wishlist.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    @Autowired
    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    // id로 위시리스트 찾기 (단일 객체 반환)
    public WishList findByMemberId(Long memberId) {
        return wishListRepository.findByMemberId(memberId);
    }

    /** wishListRepository의 findByMemberId 메소드를 호출하여
        데이터베이스에서 페이지네이션된 결과를 가져옴. **/
    public Page<WishList> findByMemberId(Long memberId, Pageable pageable) {
        return wishListRepository.findByMemberId(memberId, pageable);
    }

    // 위시리스트에 상품 추가
    @Transactional
    public void addProductToWishList(Long memberId, Product product) {
        WishList wishList = wishListRepository.findByMemberId(memberId);
        wishList.products().add(product);
        wishListRepository.save(wishList);
    }

    // 위시리스트에 상품 삭제
    @Transactional
    public void removeProductFromWishList(Long memberId, Long productId) {
        WishList wishList = wishListRepository.findByMemberId(memberId);
        wishList.products().removeIf(product -> product.product_id().equals(productId));
        wishListRepository.save(wishList);
    }
}