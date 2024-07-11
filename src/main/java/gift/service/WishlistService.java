package gift.service;

import gift.model.Member;
import gift.model.MemberRepository;
import gift.model.Wishlist;
import gift.model.WishlistRepository;
import gift.model.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WishlistService {
    private final WishlistRepository wishListRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishListRepository, MemberRepository memberRepository,
                           ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public List<Wishlist> getWishList(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        return wishListRepository.findByMemberId(member.getId());
    }

    public void addProductToWishList(String email, Long productId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        Wishlist wishList = new Wishlist(null, member.getId(), productId);
        wishListRepository.save(wishList);
    }

    public void removeProductFromWishList(String email, Long productId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        wishListRepository.deleteByMemberIdAndProductId(member.getId(), productId);
    }
}
