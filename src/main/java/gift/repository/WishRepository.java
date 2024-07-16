package gift.repository;

import gift.model.wish.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishRepository extends JpaRepository<Wish, Long> {
    Page<Wish> findAll(Pageable pageable);
}
