package gift.service;

import gift.entity.Wish;
import gift.repository.WishRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final WishRepository wishRepository;


    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;

    }

    public List<Wish> getWishlist(Long memberId) {
        return wishRepository.findByMemberId(memberId);
    }

    public boolean addWishlist(Wish wish){
        Optional<Wish> Wishlist = wishRepository.findByMemberIdAndProductId(wish.getMemberId(), wish.getProductId());
        if(Wishlist.isPresent()){
            return false;
        }
        wishRepository.save(wish);
        return true;
    }

    public boolean updateWishlistItem(Wish wish){
        Optional<Wish> Wishlist = wishRepository.findByMemberIdAndProductId(wish.getMemberId(), wish.getProductId());
        if(Wishlist.isPresent()){
            return false;
        }
        wishRepository.save(wish);
        return true;
    }

    public boolean deleteWishlist(Long userId, Long productId){
        Optional<Wish> wish = wishRepository.findByMemberIdAndProductId(userId, productId);
        if (wish.isPresent()){
            wishRepository.delete(wish.get());
            return true;
        }
        return false;
    }

}
