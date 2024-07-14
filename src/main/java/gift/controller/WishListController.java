package gift.controller;

import gift.dto.PagingRequest;
import gift.dto.PagingResponse;
import gift.model.gift.GiftResponse;
import gift.model.user.User;
import gift.model.wish.Wish;
import gift.model.wish.WishResponse;
import gift.service.GiftService;
import gift.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WishListController {

    private final WishService wishService;
    private final GiftService giftService;

    @Autowired
    public WishListController(WishService wishService, GiftService giftService) {
        this.wishService = wishService;
        this.giftService = giftService;
    }

    @GetMapping("/wish")
    public ResponseEntity<?> getGiftList(@RequestAttribute("user") User user,
                                         @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                         @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        if (user != null) {
            PagingResponse<GiftResponse> gifts = giftService.getAllGifts(page, size);
            return ResponseEntity.ok(gifts);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }

    @PostMapping("/wish/{giftId}")
    public ResponseEntity<String> addGiftToCart(@RequestAttribute("user") User user,
                                                @PathVariable Long giftId,
                                                @RequestParam(required = false, defaultValue = "1") int quantity) {
        if (user != null) {
            wishService.addGiftToUser(user.getId(), giftId, quantity);
            return ResponseEntity.ok("위시리스트에 상품이 추가되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }

    @PutMapping("/wish/{giftId}")
    public ResponseEntity<String> updateGiftQuantity(@RequestAttribute("user") User user,
                                                     @PathVariable Long giftId,
                                                     @RequestParam(name = "quantity") int quantity) {
        if (user != null) {
            wishService.updateWishQuantity(user.getId(), giftId, quantity);
            return ResponseEntity.ok("카트에서 상품수량이 변경되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }

    @DeleteMapping("/wish/{giftId}")
    public ResponseEntity<String> removeGiftFromCart(@RequestAttribute("user") User user,
                                                     @PathVariable Long giftId) {
        if (user != null) {
            wishService.removeGiftFromUser(user.getId(), giftId);
            return ResponseEntity.ok("카트에서 상품이 삭제되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }

    @GetMapping("/mywish")
    public ResponseEntity<PagingResponse<WishResponse>> getUserGifts(@RequestAttribute("user") User user,
                                                                     @ModelAttribute PagingRequest pagingRequest) {
        if (user != null) {
            PagingResponse<Wish> userWishes = wishService.getGiftsForUser(user.getId(), pagingRequest.getPage(), pagingRequest.getSize());
            List<WishResponse> wishResponses =
                    userWishes.getContent()
                            .stream()
                            .map(wish -> new WishResponse(wish.getGift().getId(), wish.getGift().getName(), wish.getGift().getPrice(), wish.getQuantity())).collect(Collectors.toList());
            PagingResponse<WishResponse> pagingResponse = new PagingResponse<>(pagingRequest.getPage(), wishResponses, pagingRequest.getSize(), userWishes.getTotalElements(), userWishes.getTotalPages());
            return ResponseEntity.ok(pagingResponse);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
