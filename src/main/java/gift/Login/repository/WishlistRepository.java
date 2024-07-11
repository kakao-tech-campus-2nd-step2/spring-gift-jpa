package gift.Login.repository;

import gift.Login.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findByMemberId(Long memberId);
}
