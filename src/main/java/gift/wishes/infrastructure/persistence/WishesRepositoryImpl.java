package gift.wishes.infrastructure.persistence;

import gift.core.domain.product.Product;
import gift.core.domain.wishes.WishesRepository;
import gift.core.domain.wishes.exception.WishAlreadyExistsException;
import gift.product.infrastructure.persistence.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
public class WishesRepositoryImpl implements WishesRepository {
    private final JpaWishRepository jpaWishRepository;

    @Autowired
    public WishesRepositoryImpl(JpaWishRepository jpaWishRepository) {
        this.jpaWishRepository = jpaWishRepository;
    }

    @Override
    public void saveWish(Long userId, Long productId) {
        if (jpaWishRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new WishAlreadyExistsException();
        }
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