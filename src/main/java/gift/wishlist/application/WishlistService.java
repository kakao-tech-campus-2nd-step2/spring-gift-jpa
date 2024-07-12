package gift.wishlist.application;

import gift.exception.type.NotFoundException;
import gift.member.domain.Member;
import gift.member.domain.MemberRepository;
import gift.product.domain.Product;
import gift.product.domain.ProductRepository;
import gift.wishlist.domain.Wishlist;
import gift.wishlist.domain.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository, MemberRepository memberRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void save(Long memberId, Long productId) {
        Member member = getMember(memberId);
        Product product = getProduct(productId);

        Wishlist wishlist = new Wishlist(member, product);
        wishlistRepository.save(wishlist);
    }

    public List<WishlistResponse> findByMemberId(Long memberId) {
        return wishlistRepository.findByMemberId(memberId)
                .stream().map(WishlistResponse::from).toList();
    }

    @Transactional
    public void delete(Long wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new NotFoundException("해당 위시리스트가 존재하지 않습니다."));

        wishlistRepository.delete(wishlist);
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("해당 상품이 존재하지 않습니다."));
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("해당 사용자가 존재하지 않습니다."));
    }
}
