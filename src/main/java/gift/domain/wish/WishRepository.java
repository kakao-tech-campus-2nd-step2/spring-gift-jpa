package gift.domain.wish;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findByMember_Id(Long memberId);
    Optional<Wish> findByMember_IdAndProduct_Id(Long memberId, Long productId);
    void deleteByMember_IdAndProduct_Id(Long memberId, Long productId);
}
