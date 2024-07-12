package gift.wishlist;

import gift.exception.InvalidProduct;
import gift.member.Member;
import gift.member.MemberRepository;
import gift.product.Product;
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

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public WishlistService(
        WishlistRepository wishlistRepository,
        ProductRepository productRepository,
        MemberRepository memberRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    // 모든 상품 조회
//    public List<Long> checkWishlist() {
//        List<Wishlist> wishlists = wishlistRepository.findAll();
//        List<Long> productIds = new ArrayList<>();
//
//        for (Wishlist wishlist : wishlists) {
//            productIds.add(wishlist.getProductId());
//        }
//
//        return productIds;
//    }
    public List<Product> checkWishlist() {
        List<Wishlist> wishlists = wishlistRepository.findAll();
        List<Product> products = new ArrayList<>();

        for (Wishlist wishlist : wishlists) {
            products.add(wishlist.getProduct());
        }

        return products;
    }


    // 상품 추가
    @Transactional
    public void addWishlist(WishRequestDto request, Member member) {
        Optional<Product> product = productRepository.findById(request.productId());

        wishlistRepository.saveAndFlush(new Wishlist(member, product.get()));
    }

    // 상품 삭제
    @Transactional
    public HttpEntity<String> deleteWishlist(Long productId, Long memberId) {
//        if (wishlistRepository.findByProductId(productId).isEmpty()) {
//            throw new InvalidProduct("잘못된 접근입니다");
//        }
//        List<Wishlist> w1 = wishlistRepository.findByProductId(productId);
//        List<Wishlist> w2 = wishlistRepository.findByMemberId(memberId);
//
//        if (w1 == w2) {
//            wishlistRepository.deleteByProductId(productId);
//            return ResponseEntity.ok("장바구니에서 제거되었습니다");
//        } else {
//            return ResponseEntity.ok("찾을 수 없습니다");
//        }
        List<Wishlist> w1 = wishlistRepository.findByProductId(productId);
        List<Wishlist> w2 = wishlistRepository.findByMemberId(memberId);

        // 특정 회원이 담은 특정 상품이 있는지 확인
        Wishlist wishlistItem = null;
        for (Wishlist item : w1) {
            if (w2.contains(item)) {
                wishlistItem = item;
                break;
            }
        }

        if (wishlistItem != null) {
            wishlistRepository.delete(wishlistItem);
            return ResponseEntity.ok("장바구니에서 제거되었습니다");
        } else {
            throw new InvalidProduct("잘못된 접근입니다");
        }
    }

}
