package gift.Service;

import gift.Entity.WishEntity;
import gift.Repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishService {

    @Autowired
    private WishRepository wishRepository;

    public List<WishEntity> findAllWishes() {
        return wishRepository.findAll();
    }

    public Optional<WishEntity> findWishById(Long id) {
        return wishRepository.findById(id);
    }

    public WishEntity saveWish(WishEntity wishEntity) {
        return wishRepository.save(wishEntity);
    }

    public void deleteWish(Long id) {
        wishRepository.deleteById(id);
    }
}
