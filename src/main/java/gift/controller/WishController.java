package gift.controller;

import gift.entity.Wish;
import gift.security.LoginMember;
import gift.service.WishService;
import gift.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
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
    List<Wish> wishes = wishService.getWishesByMemberEmail(member.getEmail());
    return ResponseEntity.ok(wishes);
  }

  @PostMapping
  public ResponseEntity<Wish> addWish(@RequestBody Wish wish, @LoginMember Member member) {
    wish.setMemberEmail(member.getEmail());
    Wish createdWish = wishService.addWish(wish);
    return ResponseEntity.ok(createdWish);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<Void> removeWish(@PathVariable Long productId, @LoginMember Member member) {
    wishService.removeWish(member.getEmail(), productId);
    return ResponseEntity.ok().build();
  }
}
