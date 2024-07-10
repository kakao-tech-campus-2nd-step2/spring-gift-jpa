package gift.Repository;

import gift.Model.ResponseWishListDTO;
import gift.Model.WishList;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    @Query("SELECT new gift.Model.ResponseWishListDTO(p.name, w.count) " +
            "FROM WishList w INNER JOIN Product p ON w.productId = p.id " +
            "WHERE w.email = :email")
    List<ResponseWishListDTO> findWishListsByEmail(@Param("email")String email);

    @Transactional
    @Modifying
    @Query("UPDATE WishList w SET w.count = :count " +
            "WHERE w.email = :email AND w.productId = :productId")
    void updateWishListCount(@Param("count")int count, @Param("email")String email, @Param("productId")Long productId);

    @Transactional
    @Modifying
    @Query("DELETE FROM WishList w WHERE w.email = :email AND w.productId = :productId")
    void deleteWishListByEmailAndProductId(@Param("email")String eamil, @Param("productId")Long productId);
}
