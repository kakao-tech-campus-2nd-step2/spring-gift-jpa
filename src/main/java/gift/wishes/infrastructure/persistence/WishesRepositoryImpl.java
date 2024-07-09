package gift.wishes.infrastructure.persistence;

import gift.core.domain.product.Product;
import gift.core.domain.wishes.WishesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class WishesRepositoryImpl implements WishesRepository {
    private final JpaWishRepository jpaWishRepository;

    @Autowired
    public WishesRepositoryImpl(JpaWishRepository jpaWishRepository) {
        this.jpaWishRepository = jpaWishRepository;
    }

    @Override
    public void saveWish(Long userId, Long productId) {
        jpaWishRepository.save(new WishEntity(0L, userId, productId));
    }

    @Override
    public void removeWish(Long userId, Long productId) {
        jpaWishRepository.deleteByUserIdAndProductId(userId, productId);
    }

    @Override
    public boolean exists(Long userId, Long productId) {
        return jpaWishRepository.existsByUserIdAndProductId(userId, productId);
    }

    @Override
    public List<Product> getWishlistOfUser(Long userId) {
        return jpaWishRepository.findAllByUserId(userId);
    }
}