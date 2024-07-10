package gift.services;


import gift.domain.Wish;
import gift.dto.WishDto;
import gift.repositories.WishRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

//    Wishlist 조회
    @Transactional
    public List<WishDto> getWishListById(Long memberId) {
        List<Wish> wishList = wishRepository.findWishListById(memberId);
        return wishList.stream()
            .map(wish -> new WishDto(
                wish.getMemberId(),
                wish.getProductId()
            ))
            .collect(Collectors.toList());
    }

//    Wish 추가
    @Transactional
    public void addWish(Long memberId, Long productId){
        Wish wish = new Wish();
        wish.setMemberId(memberId);
        wish.setProductId(productId);
        wishRepository.save(wish);
    }

//    Wish 삭제
    @Transactional
    public void deleteWish(Long memberId, Long productId) {
        wishRepository.deleteByMemberIdAndProductId(memberId, productId);
    }

}
