package gift.repository;

import gift.compositeKey.WishListId;
import gift.dto.WishDTO;
import gift.entity.WishList;
import gift.exception.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, WishListId> {
    @Query(value = "SELECT p.name, p.price, p.image_url AS imageUrl " +
            "FROM product p INNER JOIN wish_list w ON p.id = w.product_id " +
            "WHERE w.user_id = :userId", nativeQuery = true)
    List<WishDTO.wishListProduct> findByUserId(@Param("userId") int tokenUserId);

}
