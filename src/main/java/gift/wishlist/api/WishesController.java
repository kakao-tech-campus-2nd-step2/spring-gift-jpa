package gift.wishlist.api;

import gift.member.validator.LoginMember;
import gift.product.dto.ProductResponse;
import gift.product.util.ProductMapper;
import gift.wishlist.application.WishesService;
import gift.wishlist.dto.WishRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishes")
public class WishesController {

    private final WishesService wishesService;

    public WishesController(WishesService wishesService) {
        this.wishesService = wishesService;
    }

    @GetMapping
    public Page<ProductResponse> getPagedWishes(@LoginMember Long memberId,
                                                @PageableDefault(
                                                      sort = "id",
                                                      direction = Sort.Direction.DESC)
                                                Pageable pageable) {
        return wishesService.getWishlistOfMember(memberId, pageable)
                .map(ProductMapper::toResponseDto);
    }

    @PostMapping
    public void addWish(@LoginMember Long memberId,
                        @RequestBody @Valid WishRequest request) {
        wishesService.addProductToWishlist(memberId, request.productId());
    }

    @DeleteMapping("/{productId}")
    public void removeWish(@LoginMember Long memberId,
                           @PathVariable("productId") Long productId) {
        wishesService.removeProductFromWishlist(memberId, productId);
    }

}
