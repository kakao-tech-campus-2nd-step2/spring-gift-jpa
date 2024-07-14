package gift.repository;

import gift.model.wishList.WishItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishItem, Long> {

    @Query("select w from WishItem w join fetch w.item order by w.id desc")
    Page<WishItem> findAllByUserId(Long userId, Pageable pageable);

}
