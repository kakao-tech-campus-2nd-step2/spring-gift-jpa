package gift.DTO;

import java.util.ArrayList;
import java.util.List;

public class WishListDto {

  private final List<ProductDto> wishList;

  public WishListDto() {
    wishList = new ArrayList<ProductDto>();
  }

  public List<ProductDto> getWishList() {
    return this.wishList;
  }
}
