package gift.product.controller;

import gift.product.dto.ProductDTO;
import gift.product.service.WishListService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    public Page<ProductDTO> showProductList(
        @RequestHeader("Authorization") String authorization,
        Pageable pageable
    ) {
        System.out.println("[ApiWishListController] showProductList()");

        return wishListService.getAllProducts(authorization, pageable);
    }

    @PostMapping()
    public ResponseEntity<String> registerWishProduct(
        HttpServletRequest request,
        @RequestBody Map<String, Long> requestBody
    ) {
        System.out.println("[ApiWishListController] registerWishProduct()");

        wishListService.registerWishProduct(
            request,
            requestBody
        );

        return ResponseEntity.status(HttpStatus.CREATED).body("WishProduct registered successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWishProduct(
        HttpServletRequest request,
        @PathVariable Long id
    ) {
        System.out.println("[ApiWishListController] deleteWishProduct()");

        wishListService.deleteWishProduct(
            request,
            id
        );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("delete WishProduct successfully");
    }
}
