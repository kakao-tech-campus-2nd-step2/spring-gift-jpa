package gift.Service;

import gift.DTO.ProductDto;
import gift.DTO.UserDto;
import gift.DTO.WishListDto;
import gift.Repository.WishListDao;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

  private final WishListDao wishListDao;

  public WishListService(WishListDao wishListDao) {
    this.wishListDao = wishListDao;
  }

  public WishListDto addProductToWishList(WishListDto wishListDto) {
    wishListDao.save(wishListDto);
    return wishListDto;
  }

  public List<WishListDto> getWishList(UserDto user) {
    return wishListDao.findAll();
  }

  public void deleteProductToWishList(Long id) {
    wishListDao.deleteById(id);

  }
}
