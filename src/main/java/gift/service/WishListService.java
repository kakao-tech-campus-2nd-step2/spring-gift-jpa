package gift.service;

import gift.dto.WishDto;
import gift.model.wish.Wish;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WishListService {
    private final WishRepository wishRepository;

    public WishListService(WishRepository wishRepository){
        this.wishRepository = wishRepository;
    }

    public List<Wish> getAllWishes() {
        return wishRepository.findAll();
    }

    public void insertWish(WishDto wishDto) {
        Wish wish = new Wish(wishDto.getProductId(),wishDto.getMemberId(), wishDto.getAmount());
        wishRepository.save(wish);
    }

    public void deleteWish(Long productId) {
        wishRepository.deleteById(productId);
    }

    public void updateWish(Long id,WishDto wishDto){
        Wish wish = new Wish(wishDto.getProductId(),wishDto.getMemberId(), wishDto.getAmount());
        wish.setId(id);
        wishRepository.save(wish);
    }
}
