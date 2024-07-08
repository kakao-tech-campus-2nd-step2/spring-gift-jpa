package gift.controller.wishlist;

import gift.dto.ProductAmount;
import gift.dto.request.WishListRequest;
import gift.dto.response.WishedProductResponse;
import gift.service.ProductService;
import gift.service.WishListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public List<WishedProductResponse> getWishList(HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        List<ProductAmount> productIdList = wishListService.getProductIdsAndAmount(memberId);
        List<WishedProductResponse> responses = new ArrayList<>();
        for (ProductAmount productAmount : productIdList) {
            responses.add(new WishedProductResponse(productService.getProduct(productAmount.getProductId()), productAmount.getAmount()));
        }
        return responses;
    }

    @PostMapping("api/wishlist")
    public ResponseEntity<String> addWishList(HttpServletRequest request, @RequestBody WishListRequest wishListRequest) {
        Long memberId = (Long) request.getAttribute("memberId");
        boolean isAdded = wishListService.addProductToWishList(memberId, wishListRequest.getProductId(), wishListRequest.getAmount());
        if (!isAdded) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 상품");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("추가 성공");
    }

    @PutMapping("api/wishlist")
    public ResponseEntity<String> updateWishList(HttpServletRequest request,@RequestBody WishListRequest wishListRequest){
        Long memberId = (Long) request.getAttribute("memberId");
        boolean isUpdated = wishListService.updateWishList(memberId, wishListRequest.getProductId(), wishListRequest.getAmount());
        if(!isUpdated){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("위시리스트에 없는 상품");
        }
        return ResponseEntity.status(HttpStatus.OK).body("수정 성공");
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
