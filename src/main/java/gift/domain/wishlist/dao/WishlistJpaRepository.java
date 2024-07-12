package gift.domain.wishlist.dao;

import gift.domain.wishlist.entity.WishItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistJpaRepository extends JpaRepository<WishItem, Long> {

    @Query("select distinct w from WishItem w join fetch w.user u join fetch w.product p where u.id = :userId")
    List<WishItem> findAllByUserId(@Param("userId") Long userId);

    @Query("delete from WishItem w where w.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);

    @Query("delete from WishItem w where w.product.id = :productId")
    void deleteAllByProductId(@Param("productId") Long productId);
}
