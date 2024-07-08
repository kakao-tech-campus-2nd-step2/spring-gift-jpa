package gift.controller;

import gift.config.LoginUser;
import gift.dto.WishRequest;
import gift.entity.User;
import gift.entity.Wish;
import gift.service.WishService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<String> create(@RequestBody WishRequest request,
        @LoginUser User user) {
        wishService.addWish(user.getId(), request);
        return ResponseEntity.ok("생성되었습니다.");
    }

    @GetMapping
    public List<Wish> getWishes(@LoginUser User user) {
        return wishService.getWishes(user.getId());
    }

    @GetMapping("/{wishId}")
    public Wish getOneWish(@PathVariable Long wishId, @LoginUser User user) {
        return wishService.getOneWish(user.getId(), wishId);
    }

    @DeleteMapping("/{wishId}")
    public ResponseEntity<String> delete(@PathVariable Long wishId, @LoginUser User user) {
        wishService.removeWish(user.getId(), wishId);
        return ResponseEntity.ok("삭제되었습니다.");
    }

    @PutMapping("/{wishId}")
    public ResponseEntity<Wish> updateNumber(@PathVariable Long wishId, @LoginUser User user,
        @RequestBody WishRequest wishRequest) {
        wishService.updateNumber(user.getId(), wishId, wishRequest.getNumber());
        return ResponseEntity.ok(wishService.getOneWish(user.getId(), wishId));
    }
}
