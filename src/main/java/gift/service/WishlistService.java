package gift.service;

import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishlistRepository;
import gift.vo.Member;
import gift.vo.Product;
import gift.vo.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishlistRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalAccessError("위시리스트에 추가하려는 회원을 찾을 수 없습니다. "));
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("위시리스트에 추가하려는 상품을 찾을 수 없습니다. "));
    }

    public Page<Wish> getWishProductList(Long memberId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return wishlistRepository.findByMemberId(memberId, pageable);
    }

    public void addWishProduct(Long memberId, Long productId) {
        Wish wish = new Wish(getMember(memberId), getProduct(productId));
        wishlistRepository.save(wish);
    }

    public void deleteWishProduct(Long id) {
        wishlistRepository.deleteById(id);
    }

}
