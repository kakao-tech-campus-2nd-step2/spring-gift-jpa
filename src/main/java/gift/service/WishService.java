package gift.service;

import gift.domain.member.MemberRepository;
import gift.domain.product.ProductRepository;
import gift.domain.wish.WishRepository;
import gift.dto.WishAddRequestDto;
import gift.dto.WishResponseDto;
import org.springframework.stereotype.Service;

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

    public void addWish(Long memberId, WishAddRequestDto request) {
        checkProductValidation(request.getProductId());
        wishRepository.addWish(memberId, request);
    }

    public List<WishResponseDto> getAllWishes(Long id) {
        checkMemberValidation(id);
        return wishRepository.getAllWishes(id)
                .stream()
                .map(WishResponseDto::new)
                .toList();
    }

    public void deleteWish(Long memberId, Long productId) {
        checkMemberValidation(memberId);
        checkProductValidation(productId);
        wishRepository.deleteWish(memberId,productId);
    }

    private void checkProductValidation(Long productId) {
        if (productRepository.isNotValidProductId(productId)) {
            throw new IllegalArgumentException("유효하지 않은 상품 정보입니다.");
        }
    }

    private void checkMemberValidation(Long id) {
        if (memberRepository.isNotValidMemberById(id)) {
            throw new IllegalArgumentException("유효하지 않은 회원 정보입니다.");
        }
    }
}
