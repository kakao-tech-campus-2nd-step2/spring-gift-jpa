package gift.service;

import gift.model.Member;
import gift.model.MemberRepository;
import gift.model.WishList;
import gift.model.WishListRepository;
import gift.model.Product;
import gift.model.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishListService(WishListRepository wishListRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public List<WishList> getWishList(String email) {
        Member member = memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        return wishListRepository.findByMemberId(member.getId());
    }

    public void addProductToWishList(String email, Long productId) {
        Member member = memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        wishListRepository.addProductToWishList(member.getId(), productId);
    }

    public void removeProductFromWishList(String email, Long productId) {
        Member member = memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        wishListRepository.removeProductFromWishList(member.getId(), productId);
    }
}
