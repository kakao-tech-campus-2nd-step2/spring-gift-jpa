package gift.service;

import gift.entity.Wish;
import gift.repository.WishRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class WishlistService {

    private final WishRepository wishRepository;

    public WishlistService(WishRepository wishRepository){
        this.wishRepository=wishRepository;
    }
    @Transactional
    public void addWishlist(Wish wish) {
        wishRepository.save(wish);

    }

    public List<Wish> getWishlist(Long id) {
        return wishRepository.findByMemberId(id);
    }

    public void deleteWishlist(Long id){
         wishRepository.deleteById(id);
    }


}
