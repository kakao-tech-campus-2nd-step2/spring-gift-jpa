package gift.Service;

import gift.ConverterToDto;
import gift.DTO.Member;
import gift.DTO.Product;
import gift.DTO.WishList;
import gift.DTO.WishListDto;
import gift.Repository.MemberRepository;
import gift.Repository.ProductRepository;
import gift.Repository.WishListRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

  private final WishListRepository wishListRepository;
  private final MemberRepository memberRepository;
  private final ProductRepository productRepository;

  public WishListService(WishListRepository wishListRepository, ProductRepository productRepository,
      MemberRepository memberRepository) {
    this.wishListRepository = wishListRepository;
    this.memberRepository = memberRepository;
    this.productRepository = productRepository;
  }

  public WishListDto addProductToWishList(WishListDto wishListDto) {
    Long memberId = wishListDto.getMemberDto().getId();
    Long productId = wishListDto.getProductDto().getId();
    Member member = memberRepository.getById(memberId);
    Product product = productRepository.getById(productId);
    WishList wishList = new WishList(member, product);
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
