package gift.service;

import gift.dto.response.WishProductResponse;
import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.exception.WishAlreadyExistsException;
import gift.exception.WishNotFoundException;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WishListService {

    private final WishRepository wishListRepository;
    private final ProductService productService;
    private final MemberService memberService;

    public WishListService(WishRepository wishListRepository, ProductService productService, MemberService memberService) {
        this.wishListRepository = wishListRepository;
        this.productService = productService;
        this.memberService = memberService;
    }

    @Transactional
    public void addProductToWishList(Long memberId, Long productId, int amount) {
        Optional<Wish> existingWish = wishListRepository.findByMemberIdAndProductId(memberId, productId);
        if (existingWish.isPresent()) {
            throw new WishAlreadyExistsException(existingWish.get());
        }
        Product product = productService.getProduct(productId);
        Member member = memberService.getMemberById(memberId);
        Wish wish = new Wish(member, amount, product);
        wishListRepository.save(wish);
    }

    @Transactional
    public void deleteProductInWishList(Long memberId, Long productId) {
        Wish wish = wishListRepository.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(WishNotFoundException::new);
        wishListRepository.delete(wish);
    }

    @Transactional
    public void updateWishProductAmount(Long memberId, Long productId, int amount) {
        Wish wish = wishListRepository.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(WishNotFoundException::new);
        wish.setAmount(amount);
    }

    public List<WishProductResponse> getWishProductsByMemberId(Long memberId) {
        return wishListRepository.findAllByMemberIdWithProduct(memberId)
                .stream()
                .map(wish -> new WishProductResponse(wish.getProduct().getId(), wish.getProduct().getName(), wish.getProduct().getPrice(), wish.getProduct().getImageUrl(), wish.getAmount()))
                .toList();
    }

}
