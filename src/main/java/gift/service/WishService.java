package gift.service;

import gift.domain.Wish;
import gift.dto.WishDto;
import gift.repository.WishRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public void addWish(Long memberId, WishDto wishDto) {
        Wish newWish = new Wish(memberId, wishDto.getProductId());
        wishRepository.save(newWish);
    }

    public void deleteWish(Long wishId){
        wishRepository.deleteById(wishId);
    }

    public List<Wish> findByMemberId(Long id) {
        return wishRepository.findByMemberId(id);
    }
}
