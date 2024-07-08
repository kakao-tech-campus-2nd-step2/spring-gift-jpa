package gift.controller;

import gift.annotation.LoginMember;
import gift.domain.WishList;
import gift.dto.WishRequest;
import gift.service.WishListService;
import gift.utils.JwtUtil;
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
@RequestMapping("/api/wish")
public class WishListController {
    private final WishListService wishListService;
    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping
    public ResponseEntity<List<WishList>> getWishList(@LoginMember gift.domain.LoginMember member) {
        List<WishList> wishLists = wishListService.getWishListByUserId(member.getId());
        return ResponseEntity.ok(wishLists);
    }

    @PostMapping
    public ResponseEntity<Void> addProductToWishList(@RequestBody WishRequest wishRequest, @LoginMember
        gift.domain.LoginMember member) {
        wishListService.addWishList(member.getId(), wishRequest.getProductId(), wishRequest.getQuantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeWishList(@PathVariable("id") Long id, @LoginMember
        gift.domain.LoginMember member) {
        wishListService.removeWishList(id);
        return ResponseEntity.noContent().build();
    }
}
