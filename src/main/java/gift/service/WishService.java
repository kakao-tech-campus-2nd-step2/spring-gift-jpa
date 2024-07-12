package gift.service;

import gift.domain.member.Member;
import gift.domain.member.MemberRepository;
import gift.domain.product.Product;
import gift.domain.product.ProductRepository;
import gift.domain.wish.Wish;
import gift.dto.WishAddRequestDto;
import gift.dto.WishResponseDto;
import gift.exception.CustomException;
import gift.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishService {
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishService(MemberRepository memberRepository, ProductRepository productRepository) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addWish(Long memberId, WishAddRequestDto request) {
        Product product = getProduct(request.getProductId());
        Member member = getMember(memberId);
        Wish wish = member.getWishList().stream().filter(w -> w.getProduct().equals(product))
                .findFirst()
                .map(w -> {
                    w.setQuantity(w.getQuantity() + request.getQuantity());
                    return w;
                })
                .orElseGet(() -> new Wish(getMember(memberId), getProduct(request.getProductId()), request.getQuantity()));

        member.addWish(wish);
    }

    public List<WishResponseDto> getAllWishes(Long id) {
        Member member = getMember(id);
        return member.getWishList().stream().map(WishResponseDto::new).toList();
    }

    @Transactional
    public void deleteWish(Long memberId, Long productId) {
        Member member = getMember(memberId);
        checkProductValidation(productId);
        member.deleteWish(productId);
    }

    private void checkProductValidation(Long productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PRODUCT));
    }

    private Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PRODUCT));
    }
}
