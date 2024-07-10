package gift.wishlist;

import gift.exception.InvalidProduct;
import gift.member.Member;
import gift.product.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

//    private final WishlistRepository wishlistRepository;
//    private final ProductRepository productRepository;
//
//    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository) {
//        this.wishlistRepository = wishlistRepository;
//        this.productRepository = productRepository;
//    }
//
//    // post를 위한 wishlist 테이블에 insert
//    public Wishlist postWishlist(Wishlist wishlist) {
//        return wishlistRepository.save(wishlist);
//    }
//
//    // 모든 장바구니 상품 조회를 위한
//    public List<Long> getAllWishlist() {
//        List<Wishlist> wishlists = wishlistRepository.findAll();
//
//        //return
//        //return wishlistDao.findAllWish();
//    }
//
//    // 삭제하기 전 상품이 존재하는지 확인을 위한
//    public Optional<Long> getWishlistById(Long productId) {
//        //return wishlistDao.findProductById(productId);
//        //return wishlistRepository.findWishlistBy(productId);
//    } // delete를 위한 것
//
//    // 상품 삭제를 위한
//    public void deleteWishlist(Long productId) {
//
//        //wishlistDao.deleteWish(productId);
//        wishlistRepository.deleteById(productId);
//    }

    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public List<Long> checkWishlist() {
        List<Wishlist> wishlists = wishlistRepository.findAll();
        List<Long> productIds = new ArrayList<>();

        for (Wishlist wishlist : wishlists) {
            productIds.add(wishlist.getProductId());
        }

        return productIds;
    }

    public void addWishlist(WishRequestDto request, Member member) {
        wishlistRepository.saveAndFlush(new Wishlist(member.getId(), request.productId()));
    }

    @Transactional
    public HttpEntity<String> deleteWishlist(Long productId) {
        if (wishlistRepository.findByProductId(productId).isEmpty()) {
            throw new InvalidProduct("잘못된 접근입니다");
        }
        wishlistRepository.deleteByProductId(productId);
        return ResponseEntity.ok("장바구니에서 제거되었습니다");
    }

}
