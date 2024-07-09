package gift.controller;

import gift.annotation.LoginMember;
import gift.dto.LoginUserDTO;
import gift.domain.WishList;
import gift.dto.WishRequest;
import gift.service.WishListService;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishes")
public class WishListController {
    private final WishListService wishListService;
    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping
    public ResponseEntity<Optional<WishList>> getWishList(@LoginMember LoginUserDTO member) {
        Optional<WishList> wishLists = wishListService.getWishListByUserId(member.getId());
        return ResponseEntity.ok(wishLists);
    }

    @PostMapping
    public ResponseEntity<Void> addProductToWishList(@RequestBody WishRequest wishRequest, @LoginMember
    LoginUserDTO member) {
        wishListService.addWishList(member.getId(), wishRequest.getProductId(), wishRequest.getQuantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeWishList(@PathVariable("id") Long id, @LoginMember
    LoginUserDTO member) {
        wishListService.removeWishList(id);
        return ResponseEntity.noContent().build();
    }
}
