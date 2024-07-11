package gift.controller;

import gift.model.gift.GiftResponse;
import gift.model.user.User;
import gift.model.wish.WishResponse;
import gift.service.GiftService;
import gift.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
                                         @PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 5) Pageable pageable) {
        if (user != null) {
            List<GiftResponse> gifts = giftService.getAllGifts(pageable);
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
    public ResponseEntity<List<WishResponse>> getUserGifts(@RequestAttribute("user") User user,
                                                           @PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 5) Pageable pageable) {
        if (user != null) {
            List<WishResponse> wishResponses =
                    wishService.getGiftsForUser(user.getId(), pageable)
                            .stream()
                            .map(wish -> new WishResponse(wish.getGift().getId(), wish.getGift().getName(), wish.getGift().getPrice(), wish.getQuantity())).collect(Collectors.toList());
            return ResponseEntity.ok(wishResponses);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
    }
}
