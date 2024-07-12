package gift.repository;

import gift.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WishRepositoryInterface extends JpaRepository<Wish, Long> {
    List<Wish> findAllByUserId(Long userId);

    Wish findByProductId(Long productId);
}
