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
    
    WishList findByEmailAndProductId(String email, Long productId);

    void deleteWishListByEmailAndProductId(String email, Long productId);
}
