package gift.domain.wishlist.dao;

import gift.domain.wishlist.entity.WishItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistJpaRepository extends JpaRepository<WishItem, Long> {

    @Query("select w from WishItem w where w.user.id = :userId")
    List<WishItem> findAllByUserId(@Param("userId") Long userId);
}
