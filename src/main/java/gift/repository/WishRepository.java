package gift.repository;

import gift.domain.Wish;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, UUID> {
    Optional<Wish> findByMemberId(UUID memberId);
    Optional<Wish> findByMemberIdAndProductId(UUID memberId, UUID productId);
    void deleteByMemberIdAndProductId(UUID memberId, UUID productId);
}
