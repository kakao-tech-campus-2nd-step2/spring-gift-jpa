package gift.wishlist.application;

import gift.exception.type.NotFoundException;
import gift.wishlist.domain.Wishlist;
import gift.wishlist.domain.WishlistRepository;
import gift.product.domain.Product;
import gift.product.domain.ProductRepository;
import gift.member.domain.Member;
import gift.member.domain.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository, MemberRepository memberRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    public void add(String memberEmail, Long productId) {
        Member member = getMember(memberEmail);
        Product product = getProduct(productId);

        Wishlist wishlist = new Wishlist(member.getEmail(), product.getId());
        wishlistRepository.addWishlist(wishlist);
    }

    public List<WishlistResponse> findAllByMember(String memberEmail) {
        return wishlistRepository.findByMemberEmail(memberEmail)
                .stream().map(WishlistResponse::from).toList();
    }

    public void delete(Long wishlistId) {
        wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new NotFoundException("해당 위시리스트가 존재하지 않습니다."));

        wishlistRepository.deleteWishlist(wishlistId);
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("해당 상품이 존재하지 않습니다."));
    }

    private Member getMember(String memberEmail) {
        return memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new NotFoundException("해당 사용자가 존재하지 않습니다."));
    }
}
