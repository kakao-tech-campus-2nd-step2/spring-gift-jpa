package gift.controller;

import gift.annotation.LoginMember;
import gift.domain.Member;
import gift.domain.Wish;
import gift.dto.ProductIdRequest;
import gift.dto.WishRequest;
import gift.service.WishService;
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
    public ResponseEntity<List<Wish>> getWishes(
        @LoginMember Member member,
        @RequestParam(defaultValue = "0") int pageNo,
        @RequestParam(defaultValue = "10") int pageSize
    ) {

        return new ResponseEntity<>(wishService.getWishesByMember(member, pageNo, pageSize), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Wish> createWish(@RequestBody ProductIdRequest productIdRequest,
        @LoginMember Member member) {
        WishRequest wishRequest = new WishRequest(member.getId(), productIdRequest.getProductId());
        Wish wish = wishService.addWish(wishRequest);

        return new ResponseEntity<>(wish, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Wish> deleteWish(@PathVariable("id") Long id,
        @LoginMember Member member) {
        wishService.deleteWish(id, member);

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}