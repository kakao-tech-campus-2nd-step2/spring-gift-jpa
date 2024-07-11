package gift.service;

import gift.domain.member.MemberRepository;
import gift.domain.product.ProductRepository;
import gift.domain.wish.Wish;
import gift.domain.wish.WishRepository;
import gift.dto.WishAddRequestDto;
import gift.dto.WishResponseDto;
import gift.exception.CustomException;
import gift.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addWish(Long memberId, WishAddRequestDto request) {
        checkProductValidation(request.getProductId());
        Wish wish = wishRepository.findDistinctByMemberIdAndProductId(memberId, request.getProductId())
                .map(w -> {
                    w.setQuantity(w.getQuantity() + request.getQuantity());
                    return w;
                })
                .orElseGet(() -> new Wish(memberId, request.getProductId(), request.getQuantity()));

        wishRepository.save(wish);
    }

    public List<WishResponseDto> getAllWishes(Long id) {
        checkMemberValidation(id);
        return wishRepository.findAllByMemberId(id).stream().map(WishResponseDto::new).toList();
    }

    @Transactional
    public void deleteWish(Long memberId, Long productId) {
        checkMemberValidation(memberId);
        checkProductValidation(productId);
        wishRepository.deleteByMemberIdAndProductId(memberId, productId);
    }

    private void checkProductValidation(Long productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PRODUCT));
    }

    private void checkMemberValidation(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new IllegalArgumentException("유효하지 않은 회원 정보입니다.");
        }
    }
}
