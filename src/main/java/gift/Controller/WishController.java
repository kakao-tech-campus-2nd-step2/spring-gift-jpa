package gift.Controller;

import gift.DTO.ProductDto;
import gift.DTO.UserDto;
import gift.DTO.WishListDto;
import gift.LoginUser;
import gift.Service.WishListService;
import java.util.List;
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
  public ResponseEntity<List<ProductDto>> getWishList(@LoginUser UserDto user) {
    WishListDto wishList = wishListService.getWishList(user);
    return ResponseEntity.ok(wishList.wishListDto());
  }

  @PostMapping
  public ResponseEntity<ProductDto> addProductToWishList(@RequestBody ProductDto wishProduct,
    @LoginUser UserDto user) {
    ProductDto addedWishProduct = wishListService.addProductToWishList(wishProduct, user);
    // 생성된 리소스의 URI를 빌드
    var location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(addedWishProduct.getId())
      .toUri();

    return ResponseEntity.created(location).body(addedWishProduct);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ProductDto> deleteProductToWishList(@PathVariable Long id) {
    ProductDto deletedWishProduct = wishListService.deleteProductToWishList(id);
    return ResponseEntity.ok(deletedWishProduct);
  }

}
