package gift.Service;

import gift.ConverterToDto;
import gift.DTO.WishListDto;
import gift.DTO.WishList;
import gift.Repository.WishListRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

  private final WishListRepository wishListRepository;

  public WishListService(WishListRepository wishListRepository) {
    this.wishListRepository = wishListRepository;
  }

  public WishListDto addProductToWishList(WishListDto wishListDto) {
    WishList wishList = new WishList(wishListDto.getId(), wishListDto.getUserId(),
      wishListDto.getProductId());
    wishListRepository.save(wishList);
    return wishListDto;
  }

  public List<WishListDto> getWishList() {
    List<WishListDto> wishListDtos = wishListRepository.findAll().stream()
      .map(ConverterToDto::convertToWishListDto).toList();
    return wishListDtos;
  }

  public void deleteProductToWishList(Long id) {
    wishListRepository.deleteById(id);
  }
}
