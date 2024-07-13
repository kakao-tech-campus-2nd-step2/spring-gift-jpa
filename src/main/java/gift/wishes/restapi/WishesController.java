package gift.wishes.restapi;

import gift.advice.LoggedInUser;
import gift.core.PagedDto;
import gift.core.domain.product.Product;
import gift.core.domain.product.ProductService;
import gift.core.domain.wishes.WishesService;
import gift.wishes.restapi.dto.request.AddWishRequest;
import gift.wishes.restapi.dto.response.PagedWishResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@RestController
public class WishesController {
    private final WishesService wishesService;
    private final ProductService productService;

    @Autowired
    public WishesController(
            WishesService wishesService,
            ProductService productService
    ) {
        this.wishesService = wishesService;
        this.productService = productService;
    }

    @GetMapping("/api/wishes")
    public PagedWishResponse getWishes(
            @LoggedInUser Long userId,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        PagedDto<Product> pagedWishes = wishesService.getWishlistOfUser(userId, pageable);
        return PagedWishResponse.from(pagedWishes);
    }

    @PostMapping("/api/wishes")
    public void addWish(@LoggedInUser Long userId, @RequestBody AddWishRequest request) {
        Product product = productService.get(request.productId());
        wishesService.addProductToWishes(userId, product);
    }

    @DeleteMapping("/api/wishes/{productId}")
    public void removeWish(@LoggedInUser Long userId, @PathVariable Long productId) {
        Product product = productService.get(productId);
        wishesService.removeProductFromWishes(userId, product);
    }
}