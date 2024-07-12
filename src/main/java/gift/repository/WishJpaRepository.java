package gift.repository;

import gift.model.member.Member;
import gift.model.product.Product;
import gift.model.wish.Wish;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WishJpaRepository extends JpaRepository<Wish, Long> {

    Optional<Wish> findByMemberAndProduct(Member member, Product product);

    List<Wish> findByMemberId(Long memberId);

    @Query("SELECT w FROM Wish w JOIN FETCH w.member JOIN FETCH w.product WHERE w.member.id = :memberId ORDER BY w.id DESC")
    Page<Wish> findAllByMemberByIdDesc(Long memberId, Pageable pageable);
}
