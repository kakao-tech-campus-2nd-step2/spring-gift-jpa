package gift.Controller;

import gift.DTO.ProductDto;
import gift.DTO.UserDto;
import gift.LoginUser;
import gift.Repository.ProductDao;
import gift.Service.WishListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishes")
public class WishController {

  private final WishListService wishListService;

  public WishController(WishListService wishListService) {
    this.wishListService = wishListService;
  }


  @PostMapping("/add")
  public ResponseEntity<ProductDto> addProductToWishList(@RequestBody ProductDto wishProduct,
    @LoginUser UserDto user) {
    ProductDto addedWishProduct = wishListService.addProductToWishList(wishProduct, user);
    return ResponseEntity.ok(wishProduct);
  }

}
