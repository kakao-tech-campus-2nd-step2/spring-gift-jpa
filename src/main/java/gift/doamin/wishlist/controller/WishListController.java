package gift.doamin.wishlist.controller;

import gift.doamin.wishlist.dto.WishListForm;
import gift.doamin.wishlist.entity.WishList;
import gift.doamin.wishlist.service.WishListService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public void createWishList(@Valid @RequestBody WishListForm wishListForm, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        wishListService.create(userId, wishListForm);
    }

    @GetMapping
    public List<WishList> getWishList(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return wishListService.read(userId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateWishList(@Valid @RequestBody WishListForm wishListForm, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        wishListService.update(userId, wishListForm);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWishList(@Valid @RequestBody WishListForm wishListForm, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        wishListService.update(userId, wishListForm);
    }
}
