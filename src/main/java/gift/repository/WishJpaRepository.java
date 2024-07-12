package gift.repository;

import gift.domain.User;
import gift.domain.Wish;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishJpaRepository extends JpaRepository<Wish, Long> {

    Page<Wish> findByUser(User user, Pageable pageable);

    Optional<Wish> findByProductName(String productName);

    boolean existsByProductName(String productName);
}
