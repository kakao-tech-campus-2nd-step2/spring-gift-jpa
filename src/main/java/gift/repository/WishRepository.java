package gift.repository;

import gift.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
    @Query("SELECT w FROM Wish w JOIN FETCH w.member JOIN FETCH w.product WHERE w.member.id = :memberId")
    List<Wish> findByMemberId(Long memberId);

    void deleteByMemberIdAndProductId(Long memberId, Long productId);
}
