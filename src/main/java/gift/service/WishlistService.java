package gift.service;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.WishlistItem;
import gift.dto.request.WishlistNameRequest;
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

    public void addItemToWishlist(WishlistNameRequest wishlistNameRequest, String token) {
        Long memberId = Long.valueOf(tokenService.getMemberIdFromToken(token));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));
        Product product = productRepository.findById(wishlistNameRequest.getProductId()).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 상품입니다."));

        WishlistItem item = new WishlistItem(member, product);
        wishlistRepository.save(item);
    }

    public void deleteItemFromWishlist(Long productId, String token) {

        Long memberId = Long.valueOf(tokenService.getMemberIdFromToken(token));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));
        boolean itemExists = wishlistRepository.findByMemberId(member.getId())
                .stream()
                .anyMatch(item -> Long.valueOf(item.getProduct().getId()).equals(productId));

        if (!itemExists) {
            throw new MemberNotFoundException("해당 아이템이 존재하지 않습니다: " + productId);
        }

        wishlistRepository.deleteByMemberIdAndProductId(member.getId(), productId);
    }

    public Page<WishlistItem> getWishlistByMemberId(Long memberId, Pageable pageable) {
        return wishlistRepository.findByMemberId(memberId, pageable);
    }


}
