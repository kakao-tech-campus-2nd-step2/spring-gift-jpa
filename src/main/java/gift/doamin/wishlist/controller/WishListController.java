package gift.doamin.wishlist.controller;

import gift.doamin.wishlist.dto.WishForm;
import gift.doamin.wishlist.dto.WishParam;
import gift.doamin.wishlist.service.WishListService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlist")
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addWish(@Valid @RequestBody WishForm wishForm, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        wishListService.create(userId, wishForm);
    }

    @GetMapping
    public List<WishParam> getWishList(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return wishListService.read(userId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateWish(@Valid @RequestBody WishForm wishForm, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        wishListService.update(userId, wishForm);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWish(@Valid @RequestBody WishForm wishForm, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        wishListService.update(userId, wishForm);
    }
}
