package gift.domain.wishlist.controller;

import gift.config.LoginUser;
import gift.domain.user.entity.User;
import gift.domain.wishlist.dto.WishItemDto;
import gift.domain.wishlist.entity.WishItem;
import gift.domain.wishlist.service.WishlistService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistRestController {

    private final WishlistService wishlistService;

    public WishlistRestController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping
    public ResponseEntity<WishItem> create(@RequestBody WishItemDto wishItemDto, @LoginUser User user) {
        WishItem savedWishItem = wishlistService.create(wishItemDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWishItem);
    }

    @GetMapping
    public ResponseEntity<Page<WishItem>> readAll(Pageable pageable, @LoginUser User user) {
        return ResponseEntity.status(HttpStatus.OK).body(wishlistService.readAll(pageable, user));
    }

    @DeleteMapping("/{wishItemId}")
    public ResponseEntity<Void> delete(@PathVariable("wishItemId") long wishItemId, @LoginUser User user) {
        wishlistService.delete(wishItemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteAllByUser(@LoginUser User user) {
        wishlistService.deleteAllByUserId(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
