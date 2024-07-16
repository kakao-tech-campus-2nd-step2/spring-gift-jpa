package gift.service;

import gift.dto.WishDto;
import gift.model.wish.Wish;
import gift.repository.WishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WishListService {
    private final WishRepository wishRepository;

    public WishListService(WishRepository wishRepository){
        this.wishRepository = wishRepository;
    }

    public Page<Wish> getAllWishes(Pageable pageable) {
        return wishRepository.findAll(pageable);
    }

    public void insertWish(WishDto wishDto) {
        Wish wish = new Wish(wishDto.getProduct(),wishDto.getMember(),wishDto.getAmount());
        wishRepository.save(wish);
    }

    public void deleteWish(Long productId) {
        wishRepository.deleteById(productId);
    }

    public void updateWish(Long id, WishDto wishDto){
        Wish wish = wishRepository.findById(id).get();
        wish.updateWish(wishDto);
        wishRepository.save(wish);
    }
}
