package gift.controller;

import gift.model.AuthInfo;
import gift.model.WishListDTO;
import gift.service.WishListService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/wishlist")
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping
    public ResponseEntity<List<WishListDTO>> getAllWishList() {
        List<WishListDTO> wishlists = wishListService.getAllWishList();
        return ResponseEntity.ok(wishlists);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<?> getWishList(@PathVariable long memberId,
        AuthInfo authInfo) {
        long tokenId = Long.parseLong(authInfo.id());
        if (tokenId == memberId) {
            List<WishListDTO> wishLists = wishListService.getWishListById(memberId);
            return ResponseEntity.ok(wishLists);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("위시 리스트를 확인할 권한이 없습니다.");
    }

    @PostMapping("/{memberId}/product")
    public ResponseEntity<?> createWishList(@PathVariable String memberId,
        @RequestBody WishListDTO wishListDTO, AuthInfo authInfo) {
        if (authInfo.id().equals(memberId)) {
            wishListService.createWishList(wishListDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(wishListDTO);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("위시 리스트를 확인할 권한이 없습니다.");
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<?> updateWishListQuantity(@PathVariable String memberId,
        @RequestBody WishListDTO wishListDTO, AuthInfo authInfo) {
        if (authInfo.id().equals(memberId)) {
            wishListService.updateWishListQuantity(wishListDTO);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("위시 리스트를 확인할 권한이 없습니다.");
    }

    @DeleteMapping("/{memberId}/{productName}")
    public ResponseEntity<?> deleteWishList(@PathVariable String memberId,
        @PathVariable String productName,
        AuthInfo authInfo) {
        if (authInfo.id().equals(memberId)) {
            wishListService.deleteWishList(memberId, productName);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("위시 리스트를 확인할 권한이 없습니다.");
    }
}
