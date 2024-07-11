package gift.service;

import gift.model.WishlistRepository;
import gift.model.MemberRepository;
import gift.model.ProductRepository;
import gift.model.Wishlist;
import gift.model.Member;
import gift.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishlistRepository, MemberRepository memberRepository,
                           ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public List<Wishlist> getWishList(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        return wishlistRepository.findByMemberId(member.getId());
    }

    public void addProductToWishList(String email, Long productId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        Wishlist wishlist = new Wishlist(null, member, product);
        wishlistRepository.save(wishlist);
    }

    @Transactional
    public void removeProductFromWishList(String email, Long productId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        wishlistRepository.deleteByMemberIdAndProductId(member.getId(), productId);
    }
}
