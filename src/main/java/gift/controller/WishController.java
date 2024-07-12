package gift.controller;

import gift.dto.LoginUser;
import gift.entity.Wish;
import gift.service.LoginMember;
import gift.service.WishService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishes")
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<String> addWish(@PathVariable("productId") Long productId, @LoginMember LoginUser loginUser) {
        System.out.println("post");
        wishService.addWish(productId, loginUser);
        return ResponseEntity.ok("Wish added");
    }

    @GetMapping
    public List<Wish> getWishes(@LoginMember LoginUser loginUser) {
        System.out.println("get");
        return wishService.getWishesByMemberId(loginUser);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteWish(@PathVariable("productId") Long productId, @LoginMember LoginUser loginUser) {
        System.out.println("delete");
        wishService.removeWish(productId, loginUser);
        return ResponseEntity.ok("Wish deleted");
    }

}
