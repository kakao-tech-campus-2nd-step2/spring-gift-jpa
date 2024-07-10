package gift.repository;

import gift.model.Product;
import gift.model.WishProduct;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishProductRepository extends JpaRepository<WishProduct, Long> {

    @Query("select p from Product p where p.id in (select w.product.id from WishProduct w where w.member.id = :memberId)")
    List<Product> findAllByMemberId(@Param("memberId") Long memberId);

    Optional<WishProduct> findByMemberIdAndProductId(Long memberId, Long productId);
}
