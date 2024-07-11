package gift.repository;

import gift.entity.compositeKey.WishListId;
import gift.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, WishListId> {
    @Query("SELECT p.name, p.price, p.imageUrl " +
            "FROM Product p, WishList w where p.id = w.id.productId and w.id.userId = :userId")
    List<Object[]> findByUserId(@Param("userId") int tokenUserId);


}
