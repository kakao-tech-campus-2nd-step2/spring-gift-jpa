package gift.controller.wishlist;

import gift.dto.request.WishListRequest;
import gift.dto.response.WishProductResponse;
import gift.service.ProductService;
import gift.service.WishListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WishListController {

    private final WishListService wishListService;
    private final ProductService productService;

    public WishListController(WishListService wishListService, ProductService productService) {
        this.wishListService = wishListService;
        this.productService = productService;
    }

    @GetMapping("api/wishlist")
    public List<WishProductResponse> getWishList(HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        return wishListService.getWishProductsByMemberId(memberId);
    }

    @PostMapping("api/wishlist")
    public ResponseEntity<Void> addWishList(HttpServletRequest request, @RequestBody WishListRequest wishListRequest) {
        Long memberId = (Long) request.getAttribute("memberId");
        wishListService.addProductToWishList(memberId, wishListRequest.getProductId(), wishListRequest.getAmount());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("api/wishlist")
    public ResponseEntity<Void> updateWishList(HttpServletRequest request, @RequestBody WishListRequest wishListRequest) {
        Long memberId = (Long) request.getAttribute("memberId");
        wishListService.updateWishList(memberId, wishListRequest.getProductId(), wishListRequest.getAmount());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("api/wishlist")
    public ResponseEntity<String> deleteWishList(HttpServletRequest request,@RequestBody WishListRequest wishListRequest){
        Long memberId = (Long) request.getAttribute("memberId");
        boolean isDeleted = wishListService.deleteProductInWishList(memberId, wishListRequest.getProductId());
        if(!isDeleted){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("위시리스트에 없는 상품");
        }
        return ResponseEntity.status(HttpStatus.OK).body("삭제 성공");
    }

}
