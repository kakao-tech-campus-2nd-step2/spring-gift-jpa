package gift.service;

import static gift.util.Constants.PRODUCT_NOT_FOUND;
import static gift.util.Constants.WISH_ALREADY_EXISTS;
import static gift.util.Constants.WISH_NOT_FOUND;

import gift.dto.wish.WishRequest;
import gift.dto.wish.WishResponse;
import gift.exception.product.ProductNotFoundException;
import gift.exception.wish.DuplicateWishException;
import gift.exception.wish.WishNotFoundException;
import gift.model.Wish;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final ProductService productService;
    private final MemberService memberService;

    public WishService(WishRepository wishRepository, ProductService productService, MemberService memberService) {
        this.wishRepository = wishRepository;
        this.productService = productService;
        this.memberService = memberService;
    }

    public List<WishResponse> getWishlistByMemberId(Long memberId) {
        return wishRepository.findAllByMemberId(memberId).stream()
            .map(WishService::convertToDTO)
            .collect(Collectors.toList());
    }

    public WishResponse addWish(WishRequest wishRequest) {
        productService.getProductById(wishRequest.productId());

        if (wishRepository.existsByMemberIdAndProductId(wishRequest.memberId(), wishRequest.productId())) {
            throw new DuplicateWishException(WISH_ALREADY_EXISTS);
        }

        Wish wish = convertToEntity(wishRequest);
        Wish savedWish = wishRepository.create(wish);
        return convertToDTO(savedWish);
    }

    public void deleteWish(Long id) {
        if (wishRepository.findById(id).isEmpty()) {
            throw new WishNotFoundException(WISH_NOT_FOUND + id);
        }
        wishRepository.deleteById(id);
    }

    public Long getMemberIdFromRequest(HttpServletRequest request) {
        memberService.validateToken(request);
        return (Long) request.getAttribute("memberId");
    }

    // Mapper methods
    private static Wish convertToEntity(WishRequest wishRequest) {
        return new Wish(null, wishRequest.memberId(), wishRequest.productId());
    }

    private static WishResponse convertToDTO(Wish wish) {
        return new WishResponse(wish.getId(), wish.getMemberId(), wish.getProductId());
    }
}
