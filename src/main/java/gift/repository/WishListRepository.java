package gift.repository;

import gift.compositeKey.WishListId;
import gift.dto.ProductDTO;
import gift.dto.WishDTO;
import gift.entity.Product;
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
    @Query("SELECT new Product(p.id, p.name, p.price, p.imageUrl) " +
            "FROM Product p JOIN WishList w on p.id = w.id.product_id WHERE w.id.user_id = :userId")
    List<Product> findByUserId(@Param("userId") int tokenUserId);


}
