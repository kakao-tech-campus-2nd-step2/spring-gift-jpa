package gift.domain.wish;

import gift.domain.product.Product;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    @EntityGraph(attributePaths = {"member", "product"})
    List<Wish> findAll();
    @EntityGraph(attributePaths = {"member", "product"})
    List<Wish> findAllByMember_Id(@Param("memberId") Long memberId);
    Optional<Wish> findByMember_IdAndProduct_Id(Long memberId, Long productId);
}
