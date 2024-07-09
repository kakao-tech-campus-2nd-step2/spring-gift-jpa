package gift.repository;

import gift.model.Product;
import gift.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
    @Query("SELECT new gift.model.Product(p.id, p.name, p.price, p.imageUrl) " +
            "FROM products p " +
            "JOIN wishes w ON p.id = w.productId " +
            "WHERE w.memberId = :memberId")
    List<Product> findAllByMemberId(@Param("memberId") Long memberId);
    void deleteByProductId(Long productId);
}
