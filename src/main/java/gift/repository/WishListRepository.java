package gift.repository;

import gift.model.wishList.WishItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishItem, Long> {

    @Query("select w from WishItem w join fetch w.item")
    List<WishItem> findAllByUserId(Long userId);
}
