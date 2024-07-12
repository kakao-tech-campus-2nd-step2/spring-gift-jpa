package gift.repository;

import gift.model.Product;
import gift.model.User;
import gift.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
    @Query("SELECT w.product.id FROM Wish w WHERE w.user.id = :userId")
    List<Long> findProductIdsByUserId(@Param("userId") Long userId);

    void deleteByUserAndProduct(User user, Product product);
}
