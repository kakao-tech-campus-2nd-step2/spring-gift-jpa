package gift.controller;

import gift.dto.WishDTO;
import gift.model.Member;
import gift.service.WishService;
import gift.util.LoginMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishes")
public class WishController {

    private final WishService wishService;

    @Autowired
    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<List<WishDTO>> getAllWishes(@LoginMember Member member) {
        List<WishDTO> wishes = wishService.getWishesByMemberId(member.getId());
        return ResponseEntity.ok(wishes);
    }

    @PostMapping
    public ResponseEntity<WishDTO> addWish(@LoginMember Member member, @RequestBody Long productId) {
        WishDTO savedWish = wishService.addWish(member, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWish);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteWish(@LoginMember Member member, @PathVariable Long productId) {
        wishService.deleteWish(member.getId(), productId);
        return ResponseEntity.noContent().build();
    }
}
