package gift.controller;

import gift.config.LoginMember;
import gift.domain.member.Member;
import gift.response.ProductResponse;
import gift.service.WishlistService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/wishlists/products")
@RestController
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> productList(@LoginMember Member member) {
        List<ProductResponse> products = wishlistService.getProducts(member.getId());

        return ResponseEntity.ok()
            .body(products);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> productAdd(@LoginMember Member member,
        @PathVariable Long productId) {
        wishlistService.addProduct(member.getId(), productId);

        return ResponseEntity.status(HttpStatus.CREATED)
            .build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> productRemove(@LoginMember Member member,
        @PathVariable Long productId) {
        wishlistService.removeProduct(member.getId(), productId);

        return ResponseEntity.ok()
            .build();
    }

}
