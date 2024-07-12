package gift.Repository;

import gift.Model.ResponseWishListDTO;
import gift.Model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    @Query("SELECT new gift.Model.ResponseWishListDTO(p.name, w.count) " +
            "FROM WishList w INNER JOIN Product p ON w.productId = p.id " +
            "WHERE w.email = :email")
    List<ResponseWishListDTO> findWishListsByEmail(@Param("email")String email);
    
    Optional<WishList> findByEmailAndProductId(String email, Long productId);
}
