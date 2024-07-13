package gift.controller.wish;

import gift.domain.user.User;
import gift.domain.wish.Wish;
import gift.domain.wish.WishRequest;
import gift.domain.wish.WishResponse;
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

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void saveWish(@RequestBody WishRequest wishRequest, @LoginMember User loginUser) {
        wishService.saveWish(wishRequest.getProductId(), loginUser.getId(), wishRequest.getAmount());
    }

    @PutMapping("/{wishId}")
    @ResponseStatus(HttpStatus.OK)
    public void modifyWish(@PathVariable("wishId") Long wishId,
                           @RequestBody WishRequest wishRequest,
                           @LoginMember User loginUser) {
        wishService.modifyWish(wishId, wishRequest.getProductId(), loginUser.getId(), wishRequest.getAmount());
    }

    @GetMapping()
    public ResponseEntity<List<WishResponse>> getWishList(@LoginMember User loginUser) {
        List<Wish> wishes = wishService.getWishList(loginUser.getId());
        List<WishResponse> responses = WishResponse.fromModelList(wishes);
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{wishId}")
    public ResponseEntity<WishResponse> getWishDetail(@PathVariable("wishId") Long wishId,
                                                      @LoginMember User loginUser) {
        Wish wish = wishService.getWishDetail(wishId, loginUser.getId());
        return ResponseEntity.ok().body(WishResponse.fromModel(wish));
    }

    @DeleteMapping("/{wishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWish(@PathVariable("wishId") Long wishId, @LoginMember User loginUser) {
        wishService.deleteWish(wishId, loginUser.getId());
    }
}
