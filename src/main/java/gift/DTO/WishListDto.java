package gift.DTO;

import java.util.ArrayList;
import java.util.List;

public class WishListDto {

  private final List<ProductDto> wishListDto;


  public WishListDto(List<ProductDto> wishListDto) {
    this.wishListDto=wishListDto;
  }

  public List<ProductDto> getWishList() {
    return this.wishListDto;
  }
}
