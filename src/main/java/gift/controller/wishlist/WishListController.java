package gift.controller.wishlist;

import gift.dto.ProductAmount;
import gift.dto.request.WishListAddRequest;
import gift.dto.response.WishedProductResponse;
import gift.service.ProductService;
import gift.service.WishListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PutMapping("api/wishlist")
    public ResponseEntity<WishListAddRequest> updateWishList(HttpServletRequest request, @RequestBody WishListAddRequest wishListRequest) {
        Long memberId = (Long) request.getAttribute("memberId");
        wishListService.updateProductInWishList(memberId, wishListRequest.getProductId(), wishListRequest.getAmount());
        return ResponseEntity.ok(wishListRequest);
    }

}
