package gift.services;


import gift.Wish;
import gift.WishDto;
import gift.repositories.WishRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

//    Wishlist 조회
    public List<WishDto> getWishListById (Long memberId){
        List<Wish> wishList = wishRepository.findWishListById(memberId);
        List<WishDto> wishDtoList = wishList.stream().map(wish -> new WishDto(
            wish.getMemberId(),
            wish.getProductId(),
            wish.getProductName()
        )).toList();

        return wishDtoList;
    }

//    Wish 추가
    public void addWish(Long memberId, Long productId){
        wishRepository.insertWish(memberId, productId);
    }

//    Wish 삭제
    public void deleteWish(Long memberId, Long productId){
        wishRepository.deleteWish(memberId, productId);
    }

}
