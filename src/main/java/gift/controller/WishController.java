package gift.controller;

import gift.model.Wish;
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
    public ResponseEntity<List<Wish>> getAllWishes(@LoginMember Member member) {
        List<Wish> wishes = wishService.getWishesByMemberId(member.getId());
        return ResponseEntity.ok(wishes);
    }

    @PostMapping
    public ResponseEntity<Wish> addWish(@LoginMember Member member, @RequestBody Wish wish) {
        Wish savedWish = wishService.addWish(wish, member.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWish);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWish(@LoginMember Member member, @PathVariable Long id) {
        wishService.deleteWish(member.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
