package gift.controller;

import gift.domain.Member;
import gift.dto.WishRequest;
import gift.dto.WishResponse;
import gift.service.WishService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishes")
public class WishController {

    private WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }


    @GetMapping
    public ResponseEntity<Page<WishResponse>> getWishes(@LoginMember Member member,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Page<WishResponse> wishes = wishService.getWishes(member.getId(), PageRequest.of(page, size));
//        List<WishResponse> wishes = wishService.getWishes(member.getId());
        return ResponseEntity.ok(wishes);
    }

    @PostMapping
    public ResponseEntity<String> addWish(@RequestBody WishRequest wishRequest, @LoginMember Member member) {
        try {
            wishService.addWish(wishRequest, member.getId());
            return ResponseEntity.ok("위시리스트 담기 완료!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("위시리스트 담기 실패: " + e.getMessage());
        }

    }

    @DeleteMapping("/{wishId}")
    public ResponseEntity<Void> removeWish(@PathVariable Long wishId, @LoginMember Member member) {
        wishService.removeWish(wishId);
        return ResponseEntity.noContent().build();
    }

}
