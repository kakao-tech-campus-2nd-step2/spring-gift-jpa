package gift.Service;

import gift.ConverterToDto;
import gift.DTO.WishListDto;
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

  public WishListDto addProductToWishList(WishListDto wishListDto) {
    WishListEntity wishListEntity = new WishListEntity(wishListDto.getId(), wishListDto.getUserId(),
      wishListDto.getProductId());
    wishListDao.save(wishListEntity);
    return wishListDto;
  }

  public List<WishListDto> getWishList() {
    List<WishListDto> wishListDtos = wishListDao.findAll().stream()
      .map(ConverterToDto::convertToWishListDto).toList();
    return wishListDtos;
  }

  public void deleteProductToWishList(Long id) {
    wishListDao.deleteById(id);
  }
}
