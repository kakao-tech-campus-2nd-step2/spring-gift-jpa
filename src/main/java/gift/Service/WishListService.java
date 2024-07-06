package gift.Service;

import gift.DTO.ProductDto;
import gift.DTO.UserDto;
import gift.LoginUser;
import gift.Repository.WishListDao;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

  private final WishListDao wishListDao;

  public WishListService(WishListDao wishListDao) {
    this.wishListDao = wishListDao;
  }

  public ProductDto addProductToWishList(ProductDto wishProduct, UserDto user){
    wishListDao.insertWishList(wishProduct);
    return wishProduct;
  }

  public List<ProductDto> getWishList(UserDto user) {
    return wishListDao.selectWishList(user);
  }

  public ProductDto deleteProductToWishList(Long id) {
    ProductDto deletedWishProduct = wishListDao.selectWishProduct(id);
    wishListDao.deleteWishProduct(id);
    return deletedWishProduct;
  }
}
