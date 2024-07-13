package gift.service;

import gift.dto.WishResponseDto;
import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.exception.BusinessException;
import gift.repository.MemberRepository;
import gift.repository.WishRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final MemberService memberService;
    private final ProductService productService;

    @Autowired
    public WishService(WishRepository wishRepository, MemberService memberService, ProductService productService) {
        this.wishRepository = wishRepository;
        this.memberService = memberService;
        this.productService = productService;
    }

    public List<WishResponseDto> getWishlist(Long memberId) {
        List<Wish> wishes = wishRepository.findByMemberId(memberId);
        return wishes.stream()
            .map(wish -> {
                WishResponseDto response = new WishResponseDto();
                response.setId(wish.getId());
                response.setProductId(wish.getProduct().getId());
                response.setProductName(wish.getProduct().getName());
                response.setProductImageUrl(wish.getProduct().getImageUrl());
                response.setProductQuantity(wish.getQuantity());
                return response;
            }).collect(Collectors.toList());
    }

    public Wish addWishlist(Long memberId, Long productId, int quantity) {
        Optional<Wish> wishlists = wishRepository.findByMemberIdAndProductId(memberId,
            productId);
        Member member = memberService.getMemberById(memberId);
        Product product = productService.findById(productId);
        Wish wish = new Wish(member, product, quantity);

        if (wishlists.isPresent()) {
            Wish existingWish = wishlists.get();
            existingWish.setQuantity(quantity);
            return wishRepository.save(existingWish);
        }
        return wishRepository.save(wish);
    }


    public void deleteById(Long memberId, Long productId) {
        Wish wish = wishRepository.findByMemberIdAndProductId(memberId, productId).orElseThrow(() -> new BusinessException("삭제할 아이템을 찾을 수 없습니다."));
        wishRepository.delete(wish);
    }
}