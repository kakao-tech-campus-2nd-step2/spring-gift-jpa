package gift.Service;

import gift.DTO.UserDto;
import gift.DTO.WishListDto;
import gift.Repository.WishListDao;
import java.util.List;
import java.util.Optional;
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

  public Optional<WishListDto> deleteProductToWishList(Long id) {
    Optional<WishListDto> deletedWishProduct = wishListDao.findById(id);
    wishListDao.deleteById(id);
    return deletedWishProduct;
  }
}
