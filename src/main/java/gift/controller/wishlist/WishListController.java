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

    @GetMapping("api/wishlist")
    public List<WishProductResponse> getWishList(@MemberId Long memberId) {
        return wishListService.getWishProductsByMemberId(memberId);
    }

    @PostMapping("api/wishlist")
    public ResponseEntity<Void> addWishList(@MemberId Long memberId, @RequestBody WishListRequest wishListRequest) {
        wishListService.addProductToWishList(memberId, wishListRequest.getProductId(), wishListRequest.getAmount());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("api/wishlist")
    public ResponseEntity<Void> updateWishList(@MemberId Long memberId, @RequestBody WishListRequest wishListRequest) {
        wishListService.updateWishList(memberId, wishListRequest.getProductId(), wishListRequest.getAmount());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("api/wishlist")
    public ResponseEntity<Void> deleteWishList(@MemberId Long memberId, @RequestBody WishListRequest wishListRequest) {
        wishListService.deleteProductInWishList(memberId, wishListRequest.getProductId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
