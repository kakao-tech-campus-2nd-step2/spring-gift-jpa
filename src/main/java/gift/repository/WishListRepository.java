package gift.repository;

import gift.entity.compositeKey.WishListId;
import gift.entity.WishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, WishListId> {
    @Query("SELECT p.name, p.price, p.imageUrl " +
            "FROM Product p join WishList w  on p.id = w.id.productId where w.id.userId = :userId")
    Page<Object[]> findByUserId(@Param("userId") int tokenUserId, Pageable pageable);


}
