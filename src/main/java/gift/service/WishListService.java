package gift.service;

import gift.dto.response.WishProductResponse;
import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.exception.WishAlreadyExistsException;
import gift.exception.WishNotFoundException;
import gift.repository.WishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Member member = memberService.getMemberById(memberId);
        Product product = productService.getProduct(productId);

        wishListRepository.findByMemberAndProduct(member, product)
                .ifPresentOrElse(
                        wish -> {
                            throw new WishAlreadyExistsException(wish);
                        },
                        () -> {
                            Wish wish = new Wish(member, amount, product);
                            wishListRepository.save(wish);
                        }
                );
    }

    @Transactional
    public void deleteProductInWishList(Long memberId, Long productId) {
        Member member = memberService.getMemberById(memberId);
        Product product = productService.getProduct(productId);
        Wish wish = wishListRepository.findByMemberAndProduct(member, product)
                .orElseThrow(WishNotFoundException::new);
        wishListRepository.delete(wish);
    }

    @Transactional
    public void updateWishProductAmount(Long memberId, Long productId, int amount) {
        Member member = memberService.getMemberById(memberId);
        Product product = productService.getProduct(productId);
        Wish wish = wishListRepository.findByMemberAndProduct(member, product)
                .orElseThrow(WishNotFoundException::new);
        wish.changeAmount(amount);
    }

    public Page<WishProductResponse> getWishProductsByMemberId(Long memberId, Pageable pageable) {
        Member member = memberService.getMemberById(memberId);
        return wishListRepository.findAllByMember(member, pageable)
                .map(WishProductResponse::fromWish);
    }

}
