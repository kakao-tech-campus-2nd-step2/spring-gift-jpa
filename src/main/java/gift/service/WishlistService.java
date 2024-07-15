package gift.service;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.TokenAuth;
import gift.domain.WishlistItem;
import gift.dto.request.WishlistRequest;
import gift.exception.MemberNotFoundException;
import gift.repository.member.MemberSpringDataJpaRepository;
import gift.repository.product.ProductSpringDataJpaRepository;
import gift.repository.wishlist.WishlistSpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistSpringDataJpaRepository wishlistRepository;
    private final TokenService tokenService;
    private final MemberSpringDataJpaRepository memberRepository;
    private final ProductSpringDataJpaRepository productRepository;

    @Autowired
    public WishlistService(WishlistSpringDataJpaRepository wishlistRepository, TokenService tokenService, MemberSpringDataJpaRepository memberRepository, ProductSpringDataJpaRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.tokenService = tokenService;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public void addItemToWishlist(WishlistRequest wishlistRequest, String token) {
        TokenAuth tokenAuth = tokenService.findToken(token);
        Member member = tokenAuth.getMember();
        Product product = productRepository.findById(wishlistRequest.getProductId())
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 상품입니다."));

        WishlistItem item = new WishlistItem(member, product);
        wishlistRepository.save(item);
    }

    public void deleteItemFromWishlist(Long productId, String token) {
        TokenAuth tokenAuth = tokenService.findToken(token);
        Member member = tokenAuth.getMember();

        WishlistItem item = wishlistRepository.findByMemberIdAndProductId(member.getId(), productId)
                .orElseThrow(() -> new MemberNotFoundException("해당 아이템이 존재하지 않습니다: " + productId));

        wishlistRepository.delete(item);
    }

    public List<WishlistItem> getWishlistByMemberId(Long memberId) {
        return wishlistRepository.findByMemberId(memberId);
    }

    public Page<WishlistItem> getWishlistByMemberId(Long memberId, Pageable pageable) {
        return wishlistRepository.findByMemberId(memberId, pageable);
    }


}
