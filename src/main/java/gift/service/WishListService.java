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
        Product product = productService.getProductById(productId);
        Member member = memberService.getMemberById(memberId);
        Optional<Wish> existingWish = wishListRepository.findByMemberAndProduct(member, product);

        if (existingWish.isPresent()) {
            throw new WishAlreadyExistsException(existingWish.get());
        }

        Wish wish = new Wish(member, amount, product);
        wishListRepository.save(wish);
    }

    @Transactional
    public void deleteProductInWishList(Long memberId, Long productId) {
        Member member = memberService.getMemberById(memberId);
        Product product = productService.getProductById(productId);
        Wish wish = wishListRepository.findByMemberAndProduct(member, product)
                .orElseThrow(WishNotFoundException::new);
        wishListRepository.delete(wish);
    }

    @Transactional
    public void updateWishProductAmount(Long memberId, Long productId, int amount) {
        Member member = memberService.getMemberById(memberId);
        Product product = productService.getProductById(productId);
        Wish wish = wishListRepository.findByMemberAndProduct(member, product)
                .orElseThrow(WishNotFoundException::new);
        wish.changeAmount(amount);
    }

    public List<WishProductResponse> getWishProductsByMemberId(Long memberId) {
        Member member = memberService.getMemberById(memberId);
        return wishListRepository.findAllByMember(member)
                .stream()
                .map(WishProductResponse::fromWish)
                .toList();
    }

}
