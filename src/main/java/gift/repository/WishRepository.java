package gift.repository;

import gift.domain.Wish;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface WishRepository extends JpaRepository<Wish, Long> {

    List<Wish> findByMemberId(Long id);
}
