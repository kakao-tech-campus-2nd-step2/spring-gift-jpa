package gift.product.controller;

import gift.product.model.Wish;
import gift.product.service.WishListService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class ApiWishListController {

    private final WishListService wishListService;

    @Autowired
    public ApiWishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping()
    public Page<Wish> showProductList(@RequestHeader("Authorization") String authorization, Pageable pageable) {
        System.out.println("[ApiWishListController] showProductList()");
        return wishListService.getAllProducts(authorization, pageable);
    }

    @PostMapping()
    public ResponseEntity<String> registerWishProduct(HttpServletRequest request, @RequestBody Map<String, Long> requestBody) {
        System.out.println("[ApiWishListController] registerWishProduct()");
        return wishListService.registerWishProduct(request, requestBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWishProduct(HttpServletRequest request, @PathVariable Long id) {
        System.out.println("[ApiWishListController] deleteWishProduct()");
        return wishListService.deleteWishProduct(request, id);
    }
}
