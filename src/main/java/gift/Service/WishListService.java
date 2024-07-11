package gift.Service;

import gift.DTO.UserEntity;
import gift.DTO.WishListEntity;
import gift.Repository.WishListDao;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

  private final WishListDao wishListDao;

  public WishListService(WishListDao wishListDao) {
    this.wishListDao = wishListDao;
  }

  public WishListEntity addProductToWishList(WishListEntity wishListEntity) {
    wishListDao.save(wishListEntity);
    return wishListEntity;
  }

  public List<WishListEntity> getWishList(UserEntity user) {
    return wishListDao.findAll();
  }

  public void deleteProductToWishList(Long id) {
    wishListDao.deleteById(id);

  }
}
