package gift.domain.wishlist.controller;

import gift.annotation.LoginMember;
import gift.domain.member.entity.Member;
import gift.domain.wishlist.dto.ProductIdRequest;
import gift.domain.wishlist.dto.WishRequest;
import gift.domain.wishlist.dto.WishResponse;
import gift.domain.wishlist.service.WishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishes")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<List<WishResponse>> getWishes(
        @LoginMember Member member,
        @RequestParam(defaultValue = "0") int pageNo,
        @RequestParam(defaultValue = "10") int pageSize
    ) {

        return new ResponseEntity<>(wishService.getWishesByMember(member, pageNo, pageSize),
            HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WishResponse> createWish(@RequestBody ProductIdRequest productIdRequest,
        @LoginMember Member member) {
        WishRequest wishRequest = new WishRequest(member.getId(), productIdRequest.getProductId());
        WishResponse wishResponse = wishService.addWish(wishRequest);

        return new ResponseEntity<>(wishResponse, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<WishResponse> deleteWish(@PathVariable("id") Long id,
        @LoginMember Member member) {
        wishService.deleteWish(id, member);

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}