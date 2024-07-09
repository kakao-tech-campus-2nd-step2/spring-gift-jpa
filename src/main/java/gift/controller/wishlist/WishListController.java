package gift.controller.wishlist;

import gift.dto.request.WishListRequest;
import gift.dto.response.WishProductResponse;
import gift.interceptor.MemberId;
import gift.service.WishListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @PostMapping("api/wishlist")
    public ResponseEntity<Void> addProductToWishList(@MemberId Long memberId, @RequestBody WishListRequest request) {
        wishListService.addProductToWishList(memberId, request.productId(), request.amount());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("api/wishlist")
    public List<WishProductResponse> getWishProducts(@MemberId Long memberId) {
        return wishListService.getWishProductsByMemberId(memberId);
    }

    @PutMapping("api/wishlist")
    public ResponseEntity<Void> updateWishProductAmount(@MemberId Long memberId, @RequestBody WishListRequest request) {
        wishListService.updateWishProductAmount(memberId, request.productId(), request.amount());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("api/wishlist")
    public ResponseEntity<Void> deleteWishProduct(@MemberId Long memberId, @RequestBody WishListRequest request) {
        wishListService.deleteProductInWishList(memberId, request.productId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
