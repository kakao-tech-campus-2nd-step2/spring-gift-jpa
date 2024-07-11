package gift.controller;

import gift.model.wishList.WishListResponse;
import gift.service.WishListService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping("/wishes")
    public List<WishListResponse> getWishList(@RequestAttribute("userId") Long userId) {
        return wishListService.getWishList(userId);
    }

    @PostMapping("/wishes/{id}")
    public void addToWishList(@PathVariable("id") Long itemId,
        @RequestAttribute("userId") Long userId) {
        wishListService.addToWishList(userId, itemId);
    }

    @DeleteMapping("/wishes/{id}")
    public void deleteFromWishList(@PathVariable("id") Long id,
        @RequestAttribute("userId") Long userId) {
        wishListService.deleteFromWishList(id);
    }
}