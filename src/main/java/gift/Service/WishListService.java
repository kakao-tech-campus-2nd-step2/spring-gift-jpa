package gift.Service;

import gift.DTO.ProductDto;
import gift.LoginUser;
import gift.Repository.WishListDao;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

  private final WishListDao wishListDao;

  public WishListService(WishListDao wishListDao) {
    this.wishListDao = wishListDao;
  }

  public ProductDto addProductToWishList(ProductDto wishProduct, LoginUser user){
    wishListDao.insertWishList(wishProduct);
    return wishProduct;
  }
}
