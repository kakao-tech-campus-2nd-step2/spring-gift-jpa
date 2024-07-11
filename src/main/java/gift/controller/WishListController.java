package gift.controller;

import gift.model.WishListItem;
import gift.service.WishListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping
    public ResponseEntity<List<WishListItem>> getWishList(@RequestHeader("Authorization") String token) {
        List<WishListItem> wishList = wishListService.getWishListByToken(token);
        return ResponseEntity.ok(wishList);
    }

    @PostMapping
    public ResponseEntity<Void> addWishListItem(@RequestHeader("Authorization") String token, @RequestBody WishListItem item) {
        wishListService.addWishListItem(token, item);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishListItem(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        wishListService.deleteWishListItem(token, id);
        return ResponseEntity.ok().build();
    }
}
