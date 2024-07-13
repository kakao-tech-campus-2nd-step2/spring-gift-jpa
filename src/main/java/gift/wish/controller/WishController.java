package gift.wish.controller;

import gift.user.dto.UserDto;
import gift.wish.dto.WishDto;
import gift.security.LoginMember;
import gift.wish.service.WishService;
import gift.user.entity.User;
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
  public ResponseEntity<List<WishDto>> getAllWishes(@LoginMember User member) {
    List<WishDto> wishes = wishService.getWishesByMemberEmail(member.getEmail());
    return ResponseEntity.ok(wishes);
  }

  @PostMapping
  public ResponseEntity<WishDto> addWish(@RequestBody WishDto wishDto, @LoginMember User member) {
    wishDto.setUser(new UserDto(member.getId(), member.getEmail(), member.getPassword(), member.getRole()));
    WishDto createdWish = wishService.addWish(wishDto);
    return ResponseEntity.ok(createdWish);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<Void> removeWish(@PathVariable Long productId, @LoginMember User member) {
    wishService.removeWish(member.getEmail(), productId);
    return ResponseEntity.ok().build();
  }
}
