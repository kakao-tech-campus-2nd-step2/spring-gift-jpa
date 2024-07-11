package gift.Controller;

import gift.DTO.UserEntity;
import gift.DTO.WishListEntity;
import gift.LoginUser;
import gift.Service.WishListService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/wishes")
public class WishController {

  private final WishListService wishListService;

  public WishController(WishListService wishListService) {
    this.wishListService = wishListService;
  }

  @GetMapping
  public ResponseEntity<List<WishListEntity>> getWishList(@LoginUser UserEntity user) {
    List<WishListEntity> wishList = wishListService.getWishList(user);
    return ResponseEntity.ok(wishList);
  }

  @PostMapping
  public ResponseEntity<WishListEntity> addProductToWishList(@RequestBody WishListEntity wishProduct,
    @LoginUser Optional<UserEntity> user) {
    WishListEntity addedWishProduct = wishListService.addProductToWishList(wishProduct);

    // 생성된 리소스의 URI를 빌드
    var location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(addedWishProduct.getId())
      .toUri();

    return ResponseEntity.created(location).body(addedWishProduct);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProductToWishList(@PathVariable Long id) {
    wishListService.deleteProductToWishList(id);
    return ResponseEntity.noContent().build();

  }

}
