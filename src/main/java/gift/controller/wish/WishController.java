package gift.controller.wish;

import gift.domain.user.User;
import gift.service.wish.WishService;
import gift.validation.LoginMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishes")
public class WishController {

    private final WishService wishService;

    @Autowired
    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getWishlist(@LoginMember User user) {
        if (user != null) {
            List<String> wishlist = wishService.getWishlistByUser(user);
            return ResponseEntity.ok(wishlist);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 사용자 정보를 주입받지 못한 경우 401 반환
    }

    @PostMapping
    public ResponseEntity<?> addToWishlist(@RequestBody String product, @LoginMember User user) {
        if (user != null) {
            wishService.addToWishlist(user, product);
            return ResponseEntity.status(HttpStatus.CREATED).build(); // 성공적으로 추가된 경우 201 반환
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 사용자 정보를 주입받지 못한 경우 401 반환
    }

    @DeleteMapping
    public ResponseEntity<?> removeFromWishlist(@RequestBody String product, @LoginMember User user) {
        if (user != null) {
            wishService.removeFromWishlist(user, product);
            return ResponseEntity.ok().build(); // 성공적으로 삭제된 경우 200 반환
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 사용자 정보를 주입받지 못한 경우 401 반환
    }
}
