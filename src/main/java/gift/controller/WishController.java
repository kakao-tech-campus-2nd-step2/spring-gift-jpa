package gift.controller;

import gift.annotation.LoginMember;
import gift.domain.Member;
import gift.domain.Wish;
import gift.dto.WishDto;
import gift.service.WishService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlist")
public class WishController {
    private final WishService wishService;
    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<Page<Wish>> getWishlist(@LoginMember Member member,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Wish> wishItems = wishService.findPagedWishesByMemberId(member.getId(), pageable);
        return ResponseEntity.ok(wishItems);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addWish(@Valid @RequestBody WishDto wishDto, @LoginMember Member member) {
        wishService.addWish(member.getId(), wishDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("위시 추가 성공");
    }

    @PostMapping("/delete/{wishId}")
    public ResponseEntity<String> deleteWish(@PathVariable("wishId") Long wishId, @LoginMember Member member) {
        wishService.deleteWish(wishId);
        return ResponseEntity.status(HttpStatus.OK).body("위시 삭제 성공");
    }

}
