package gift.Repository;

import gift.Model.Member;
import gift.Model.Product;
import gift.Model.Wishlist;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
    @Transactional
    @Query("SELECT wishlist.product FROM Wishlist wishlist WHERE wishlist.member.email = :email")
    List<Product> findAllByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Wishlist (member_id, product_id) VALUES (:memberId, :productId)", nativeQuery = true)
    void addProductInWishlist(@Param("memberId") Long memberId, @Param("productId") Long productId);

    @Transactional
    @Query("DELETE FROM Wishlist wishlist WHERE wishlist.id = :id")
    void deleteByWishlistId(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Wishlist wishlist SET wishlist.member = null, wishlist.product = null WHERE wishlist.member.email= :email AND wishlist.product.id = :id")
    void changeProductMemberNull(@Param("email") String email, @Param("id") Long id);

    @Transactional
    @Query("SELECT wishlist.id FROM Wishlist wishlist WHERE wishlist.member.email = :email AND wishlist.product.id = :id")
    Long getWishlistId(@Param("email") String email, @Param("id") Long id);

}
