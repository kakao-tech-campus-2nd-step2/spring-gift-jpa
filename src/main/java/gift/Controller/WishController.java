package gift.Controller;

import gift.DTO.WishRequest;
import gift.Entity.UserEntity;
import gift.Entity.WishEntity;
import gift.Service.WishService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishes")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody WishRequest request, UserEntity user) {
        wishService.addWish(user.getId(), request);
        return ResponseEntity.ok(getWishes(user));
    }

    @GetMapping
    public List<WishEntity> getWishes(UserEntity user) {
        return wishService.getWishes(user.getId());
    }

    @DeleteMapping("/{wishId}")
    public ResponseEntity<?> delete(@PathVariable Long wishId, UserEntity user) {
        wishService.removeWish(user.getId(), wishId);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}