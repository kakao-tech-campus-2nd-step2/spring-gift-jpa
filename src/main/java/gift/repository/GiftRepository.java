package gift.repository;

import gift.model.Gift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Long> {
}
