package gift.domain.wishlist.dao;

import gift.domain.wishlist.entity.WishItem;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistJpaRepository extends JpaRepository<WishItem, Long> {

    @Query("select distinct w from WishItem w join fetch w.user u join fetch w.product p where u.id = :userId")
    Page<WishItem> findAllByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("delete from WishItem w where w.user.id = :userId")
    @Modifying
    void deleteAllByUserId(@Param("userId") Long userId);

    @Query("delete from WishItem w where w.product.id = :productId")
    @Modifying
    void deleteAllByProductId(@Param("productId") Long productId);
}
